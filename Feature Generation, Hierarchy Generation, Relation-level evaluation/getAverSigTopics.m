function aver_sig_topics = getAverSigTopics(vtag, p_sig_topics)
% get the average probability value of the significant topics
% the significant topics are defined by which prob. values are over or equal to p.
% input: the topical vector representation vtag, the threshold for
% selecting the significant topics, p_sig_topics.
    aver_sig_topics = mean(vtag(vtag >= p_sig_topics));
    if isnan(aver_sig_topics)
        aver_sig_topics=0;
    end
end

