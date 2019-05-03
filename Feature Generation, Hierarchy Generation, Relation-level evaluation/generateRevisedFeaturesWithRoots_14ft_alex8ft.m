% generate the s1+s2+s3+s4 feature = 14ft_ori + alex8ft
function feature_matrix = generateRevisedFeaturesWithRoots_14ft_alex8ft(tag_pair_root_cell_array,pzt,ptz,pz,taglist,p_sig_topics,hm_taglist,co_occ_mat,co_occ_res_mat,freq_count_per_tag,res_count_per_tag,hm_nodelist,D_graph)
    % revised 4 features in the topic distribution related features
    %pz = (1/600)*ones(1,600); % need to be changed
    [m,n] = size(tag_pair_root_cell_array);
    
    if (n==2)
        tag_pair_root_cell_array = [tag_pair_root_cell_array tag_pair_root_cell_array(:,2)];
    end
    %p = 1/600;
    
    cossim = zeros(m,1);
    
    overlapped = zeros(m,1);
    
    gen_jaccard = zeros(m,1);
    %max_entry_diff = zeros(m,1);
    %max_entry_diff_overlapped = zeros(m,1);
    %gen_jaccard_overlapped = zeros(m,1);

    %aver_sig_diff_overlapped = zeros(m,1);
    %num_rate_sig_diff_overlapped = zeros(m,1); 
    num_rate_sig_overlapped_a = zeros(m,1);
    num_rate_sig_overlapped_b = zeros(m,1);
    
    aver_sig_diff = zeros(m,1);
    num_sig_diff = zeros(m,1); 
    max_entry_diff = zeros(m,1);
    
    entropy_a = zeros(m,1);
    entropy_b = zeros(m,1);
    entropy_diff = zeros(m,1);
    
    kl_p_q = zeros(m,1);
    kl_q_p = zeros(m,1);

    pw1w2 = zeros(m,1);
    pw2w1 = zeros(m,1);

    pw1 = zeros(m,1);
    pw2 = zeros(m,1);
    pw1w2joint = zeros(m,1);
    %pw1w2jointtest = zeros(m,1);
    
    pw1w2R = zeros(m,1); % p(w1|w2,R)
    pw2w1R = zeros(m,1); % p(w2|w1,R)
    
    pw1R = zeros(m,1); % p(w1|R)
    pw2R = zeros(m,1); % p(w2|R)
    pw1w2jointR = zeros(m,1); % p(w1,w2|R)
    pw1w2jointRtest = zeros(m,1); % p(w2,w1|R)
    
    support = zeros(m,1);
    confidence = zeros(m,1);
    cosinesim = zeros(m,1);
    inclusion = zeros(m,1);
    generalisation = zeros(m,1);
    mutual_overlapping1 = zeros(m,1);
    mutual_overlapping2 = zeros(m,1);
    tsearch = zeros(m,1);
    
    for i=1:m
        %tagpairdbpedia(i,1);
        %tagpairdbpedia(i,2);
        %tag_pair_root_cell_array(i,1) % for testing
        %tag_pair_root_cell_array(i,2) % for testing
        %tag_pair_root_cell_array(i,3) % for testing
        
        % get topic distribution vector
        [vtag1,index1] = getvector(lower(tag_pair_root_cell_array(i,1)),taglist,pzt);
        if (index1 == 0)
            str = lower(tag_pair_root_cell_array(i,1));
            [vtag1,index1] = getvector([str{1} ','], taglist, pzt);
        end
        %index1
        
        [vtag2,index2] = getvector(lower(tag_pair_root_cell_array(i,2)),taglist,pzt);
        if (index2 == 0)
            str = lower(tag_pair_root_cell_array(i,2));
            [vtag2,index2] = getvector([str{1} ','], taglist, pzt);
        end    
        %index2
        %[vtag3,index3] = getvector(tag_pair_cell_array(i,3),taglist,pzt);
        
        %% [deleted] similarity and divergence features
        % revised: get cosine sim rather than divergence
        % revised: deleted
         cossim(i) = getcosinesim(vtag1,vtag2);
    
        % get kl div
        %kl_p_q(i) = KLDiv(vtag1,vtag2);
        %kl_q_p(i) = KLDiv(vtag2,vtag1);
    
        % get generalised jaccard
        gen_jaccard(i) = fuzzySetSimilarityGeneralisedJaccard(vtag1,vtag2);
        
        %% topic distribution related features [overlapped]
        % revised: get the number of overlapped significant topics
        %overlapping(i) = size(intersect(getImportantTopics(vtag1,overlapping_threshold),getImportantTopics(vtag2,overlapping_threshold)),1) - 1;
        overlapped_logic = (vtag1>p_sig_topics) & (vtag2>p_sig_topics);
        overlapped(i) = sum(overlapped_logic);
        
%         if (overlapped(i) ~= 0)
%             gen_jaccard_overlapped(i) = fuzzySetSimilarityGeneralisedJaccard(vtag1(overlapped_logic),vtag2(overlapped_logic));
%         else
%             gen_jaccard_overlapped(i) = 0;
%         end

        % revised: get difference of rate of the "overlapped" significant topics [deleted]
        %num_rate_sig_diff_overlapped(i) = getNumSigTopics(vtag1, p_sig_topics) - getNumSigTopics(vtag2, p_sig_topics);
%         if (overlapped(i) ~= 0)
%             num_rate_sig_diff_overlapped(i) = overlapped(i)/sum(vtag1>p_sig_topics) - overlapped(i)/sum(vtag2>p_sig_topics);
%         else
%             num_rate_sig_diff_overlapped(i) = 0;
%         end

         if (overlapped(i) ~= 0)
             num_rate_sig_overlapped_a(i) = overlapped(i)/sum(vtag1>p_sig_topics);
             num_rate_sig_overlapped_b(i) = overlapped(i)/sum(vtag2>p_sig_topics);
         else
             num_rate_sig_overlapped_a(i) = 0;
             num_rate_sig_overlapped_b(i) = 0;
         end
        
        % revised: get difference of the average probability value of the "overlapped" significant topics
%         if (overlapped(i) ~= 0)
%             aver_sig_diff_overlapped(i) = sum(vtag1(overlapped_logic))/overlapped(i) - sum(vtag2(overlapped_logic))/overlapped(i);
%         else
%             aver_sig_diff_overlapped(i) = 0;
%         end
        
        % revised: get max entry difference of the "overlapped" signficant topics 
%         if (overlapped(i) ~= 0)
%             max_entry_diff_overlapped(i) = max(vtag1(overlapped_logic))-max(vtag2(overlapped_logic));
%         else
%             max_entry_diff_overlapped(i) = 0;
%         end
        %% topic distribution related features [combined]
%         % get difference of the average probability value of the significant topics
         aver_sig_diff(i) = getAverSigTopics(vtag1, p_sig_topics) - getAverSigTopics(vtag2, p_sig_topics);
%         
%         % get difference of number of the significant topics
         num_sig_diff(i) = getNumSigTopics(vtag1, p_sig_topics) - getNumSigTopics(vtag2, p_sig_topics);
%         
%         % get max entry difference
         max_entry_diff(i) = max(vtag1)-max(vtag2);
        
        %% topic distribution related features [all]
        % get entropy
        entropy_a(i) = getEntropy(vtag1);
        entropy_b(i) = getEntropy(vtag2);
        %entropy_diff(i) = entropy_a(i) - entropy_b(i);
        
        % get kl div
        kl_p_q(i) = KLDiv(vtag1,vtag2);
        kl_q_p(i) = KLDiv(vtag2,vtag1);
        
        %% probabilistic association features
        % get topical word probabilistic association
        pw1w2(i) = getpww(index1,index2,ptz,pzt);
        pw2w1(i) = getpww(index2,index1,ptz,pzt);
    
        % get joint two word probability
        pw1(i) = getpw(index1,ptz,pz);
        pw2(i) = getpw(index2,ptz,pz);
        %%pw1w2joint(i) = getpww(index1,index2,ptz,pzt) * getpw(index2,ptz,pz);
        %%pw1w2jointtest(i) = getpww(index2,index1,ptz,pzt) * getpw(index1,ptz,pz);
        pw1w2joint(i) = pw1w2(i)*pw2(i);
        %pw1w2jointtest(i) = pw2w1(i)*pw1(i);
        
        % so far we assume that R contains only one tag concept
        % get topical word conditional probabilistic association
        if (strcmp(tag_pair_root_cell_array(i,2),tag_pair_root_cell_array(i,3)))
            pw1w2R(i) = pw1w2(i);
            pw2w1R(i) = pw2w1(i);
            pw1w2jointR(i) = pw1w2joint(i);
        else
            [~,indexroot] = getvector(lower(tag_pair_root_cell_array(i,3)),taglist,pzt);
            if (indexroot == 0)
                str = lower(tag_pair_root_cell_array(i,3));
                [~,indexroot] = getvector([str{1} ','], taglist, pzt);
            end
            pw1w2R(i) = getpwwR(index1,index2,indexroot,ptz,pzt,pz);
            pw2w1R(i) = getpwwR(index2,index1,indexroot,ptz,pzt,pz);
            %pw1R(i) = getpww(index1,indexroot,ptz,pzt);
            pw2R(i) = getpww(index2,indexroot,ptz,pzt);
            pw1w2jointR(i) = pw1w2R(i)*pw2R(i);
            %pw1w2jointRtest(i) = pw2w1R(i)*pw1R(i);
        end
        %i
        
        tag1 = lower(tag_pair_root_cell_array{i,1});
        tag2 = lower(tag_pair_root_cell_array{i,2});
        index1 = hm_taglist.get(tag1);
        if (size(index1,1) == 0)
            str = lower(tag_pair_root_cell_array(i,1));
            index1 = hm_taglist.get([str{1} ',']);
        end
        if (size(index1,1) == 0)
            str = lower(tag_pair_root_cell_array(i,1));
            str = str{1};
            tag1 = str(1:(size(str,2)-1));
            index1 = hm_taglist.get(tag1);
        end
         if (size(index1,1) == 0)
             tag1 = lower(tag_pair_root_cell_array{i,1});
             if (strcmp(tag1,'false'))
                 tag1 = upper(tag1);
                 index1 = hm_taglist.get(tag1);
             end    
         end
        %index1
        
        index2 = hm_taglist.get(tag2);
        if (size(index2,1) == 0)
            str = lower(tag_pair_root_cell_array(i,2));
            index2 = hm_taglist.get([str{1} ',']);
        end
        if (size(index2,1) == 0)
            str = lower(tag_pair_root_cell_array(i,2));
            str = str{1};
            tag2 = str(1:(size(str,2)-1));
            index2 = hm_taglist.get(tag2);
        end
        if (size(index2,1) == 0)
            tag2 = lower(tag_pair_root_cell_array{i,2}); 
            if (strcmp(tag2,'false'))
                 tag2 = upper(tag2);
                 index2 = hm_taglist.get(tag2);
             end    
        end
         
        support(i) = co_occ_mat(index1,index2);
        confidence(i) = support(i)/freq_count_per_tag(index2);
        
        cosinesim(i) = getcosinesim(co_occ_res_mat(index1,:),co_occ_res_mat(index2,:));
        
        inclusion(i) = co_occ_res_mat(index1,index2)/res_count_per_tag(index1);
        generalisation(i) = co_occ_res_mat(index1,index2)/res_count_per_tag(index2);
        
        mutual_overlapping1(i) = co_occ_res_mat(index1,index2)/min(res_count_per_tag(index1),res_count_per_tag(index2));
        mutual_overlapping2(i) = co_occ_res_mat(index1,index2)/max(res_count_per_tag(index1),res_count_per_tag(index2));
        
        node_ind1=hm_nodelist.get(tag1);
        node_ind2=hm_nodelist.get(tag2);
        tsearch(i) = D_graph(node_ind1,node_ind2);
        %i
    end
    % S1+S2+S3+S4: 22ft
    feature_matrix = [cossim,kl_p_q,kl_q_p,gen_jaccard,max_entry_diff,aver_sig_diff,num_sig_diff,overlapped,pw1w2,pw2w1,pw1w2joint,pw1w2R,pw2w1R,pw1w2jointR support confidence cosinesim inclusion generalisation mutual_overlapping1 mutual_overlapping2 tsearch];
        
    % FINALLY USED: original 14ft
    %feature_matrix = [cossim,kl_p_q,kl_q_p,gen_jaccard,max_entry_diff,aver_sig_diff,num_sig_diff,overlapped,pw1w2,pw2w1,pw1w2joint,pw1w2R,pw2w1R,pw1w2jointR];
    
    %%feature_matrix = [cossim,kl_p_q,kl_q_p,gen_jaccard,max_entry_diff_overlapped,aver_sig_diff_overlapped,num_rate_sig_diff_overlapped,overlapping,pw1w2,pw2w1,pw1w2joint,pw1w2R,pw2w1R,pw1w2jointR];
    
    % revised 10 ft [not used]
    %feature_matrix = [cossim,kl_p_q,kl_q_p,max_entry_diff_overlapped,aver_sig_diff_overlapped,num_rate_sig_diff_overlapped,overlapped,pw1w2R,pw2w1R,pw1w2jointR];
    
    % new revised 11ft
    %feature_matrix = [overlapped,gen_jaccard_overlapped,num_rate_sig_overlapped_a,num_rate_sig_overlapped_b,entropy_a,entropy_b,kl_p_q,kl_q_p,pw1w2R,pw2w1R,pw1w2jointR];
    
    % new revised 14ft = 11ft+3ft
    %feature_matrix = [overlapped,gen_jaccard_overlapped,num_rate_sig_overlapped_a,num_rate_sig_overlapped_b,entropy_a,entropy_b,kl_p_q,kl_q_p,pw1w2,pw2w1,pw1w2joint,pw1w2R,pw2w1R,pw1w2jointR];
    
    % new combined revised 14ft
    %feature_matrix = [overlapped,gen_jaccard_overlapped,num_rate_sig_overlapped_a,num_rate_sig_overlapped_b,max_entry_diff,aver_sig_diff,num_sig_diff,entropy_a,entropy_b,kl_p_q,kl_q_p,pw1w2R,pw2w1R,pw1w2jointR];
    %feature_matrix = [kl_p_q,kl_q_p,overlapped,max_entry_diff,aver_sig_diff,num_sig_diff,entropy_a,entropy_b,gen_jaccard_overlapped,num_rate_sig_overlapped_a,num_rate_sig_overlapped_b,pw1w2R,pw2w1R,pw1w2jointR];
    
    % new combined revised 17ft = 14ft+3ft
    %feature_matrix = [overlapped,gen_jaccard_overlapped,num_rate_sig_overlapped_a,num_rate_sig_overlapped_b,aver_sig_diff,num_sig_diff,max_entry_diff,entropy_a,entropy_b,kl_p_q,kl_q_p,pw1w2,pw2w1,pw1w2joint,pw1w2R,pw2w1R,pw1w2jointR];
    %feature_matrix = [kl_p_q,kl_q_p,overlapped,max_entry_diff,aver_sig_diff,num_sig_diff,entropy_a,entropy_b,gen_jaccard_overlapped,num_rate_sig_overlapped_a,num_rate_sig_overlapped_b,pw1w2,pw2w1,pw1w2joint,pw1w2R,pw2w1R,pw1w2jointR];
    
    % new combined revised 18ft (= 17ft + cosine = 4+(5+3)+6)
    %feature_matrix = [cossim,kl_p_q,kl_q_p,overlapped,max_entry_diff,aver_sig_diff,num_sig_diff,entropy_a,entropy_b,gen_jaccard_overlapped,num_rate_sig_overlapped_a,num_rate_sig_overlapped_b,pw1w2,pw2w1,pw1w2joint,pw1w2R,pw2w1R,pw1w2jointR];
    
    % further revised 11ft = 5ft + 6ft = 5ft + (3ft+3ft)
    %feature_matrix = [overlapped,gen_jaccard,entropy_diff,kl_p_q,kl_q_p,pw1w2,pw2w1,pw1w2joint,pw1w2R,pw2w1R,pw1w2jointR];
    
    % further revised without root 8ft = 5ft + 3ft
    %feature_matrix = [overlapped,gen_jaccard,entropy_diff,kl_p_q,kl_q_p,pw1w2,pw2w1,pw1w2joint];
    % direct output a table?
    
    % FINALLY USED: final 18ft
    %feature_matrix = [cossim,kl_p_q,kl_q_p,gen_jaccard,max_entry_diff,aver_sig_diff,num_sig_diff,entropy_a,entropy_b,overlapped,num_rate_sig_overlapped_a,num_rate_sig_overlapped_b,pw1w2,pw2w1,pw1w2joint,pw1w2R,pw2w1R,pw1w2jointR];
end