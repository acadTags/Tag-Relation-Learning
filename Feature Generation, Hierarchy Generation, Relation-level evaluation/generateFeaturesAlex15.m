function feature_matrix = generateFeaturesAlex15(tag_pair_root_cell_array,hm_taglist,co_occ_mat,co_occ_res_mat,freq_count_per_tag,res_count_per_tag,hm_nodelist,D_graph)
    % cite: A supervised learning approach to detect subsumption relations between tags in folksonomies.
    % the features proposed in this paper
    [m,n] = size(tag_pair_root_cell_array);
    support = zeros(m,1);
    confidence = zeros(m,1);
    cosinesim = zeros(m,1);
    inclusion = zeros(m,1);
    generalisation = zeros(m,1);
    mutual_overlapping1 = zeros(m,1);
    mutual_overlapping2 = zeros(m,1);
    tsearch = zeros(m,1);
    
    for i=1:m
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
        %index2
        
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
    end
    
    feature_matrix = [support confidence cosinesim inclusion generalisation mutual_overlapping1 mutual_overlapping2 tsearch];
end