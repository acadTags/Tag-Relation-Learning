function [ currentLevelRelations_pruned, index_pruned ] = hierarchyPruning( currentLevelRelations,previousLevelRelations )
% pruning the current hierarchies generated, the input should be a ranked
% current hierarchy in descendent order in terms of SVM prediction score.
    currentLevelRelations_pruned = [];
    index_pruned = [];
    hm = java.util.HashMap;
    % save all concepts in the previousLevelRelations to the hm.
    [m,n]=size(previousLevelRelations);
    for i=1:m
        for j=1:n
            % transform from cell to string
            cell = previousLevelRelations(i,j);
            str = cell{1};
            % put key-value pair to the HashMap hm
            hm.put(str,1);
        end
    end
    
    [m,n]=size(currentLevelRelations);
    for i=1:m
        %for j=1:n
            % transform from cell to string
            cell = currentLevelRelations(i,1);
            str = cell{1};
            if (not(hm.containsKey(str)))
                currentLevelRelations_pruned = [currentLevelRelations_pruned;currentLevelRelations(i,:)];
                index_pruned = [index_pruned;i];
                hm.put(str,1);
            end
        %end
    end
end

