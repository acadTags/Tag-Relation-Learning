function [] = funOutputAsLibSVMFormat( outputFileName,training_data )
% 
    [m,n] = size(training_data);
    
    % construct the formatting operator.
    formatting_operator = '%+f';
    for i=1:n-1
        formatting_operator = [formatting_operator, ' ', num2str(i), ':%.15f'];
    end    
    formatting_operator = [formatting_operator, '\n'];
    
    fid = fopen(strcat(outputFileName,'.txt'),'wt');
    if fid>0
        for k=1:m
            %fprintf(fid,'%+f 1:%.15f 2:%.15f 3:%.15f 4:%.15f 5:%.15f 6:%.15f 7:%.15f 8:%.15f 9:%.15f 10:%.15f 11:%.15f 12:%.15f 13:%.15f 14:%.15f\n',training_data(k,:));
            fprintf(fid,formatting_operator,training_data(k,:));
        end
        fclose(fid);
    end
end

