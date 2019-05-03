function num_sig_topics = getNumSigTopics(vtag, p_sig_topics)
% get the number of the significant topics
% the significant topics are defined by which prob. values are over or equal to p.
% input: the topical vector representation vtag, the threshold for
% selecting the significant topics, p_sig_topics.
    num_sig_topics = sum((vtag >= p_sig_topics)==1);
end



