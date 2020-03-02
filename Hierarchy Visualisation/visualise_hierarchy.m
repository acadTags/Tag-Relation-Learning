% set the hierarchy_mat_name to the one to be visualised.
hierarchy_mat_name = 'data_mining_dbpedia_svm_14ft';
load([hierarchy_mat_name '.mat'])
relations = eval(hierarchy_mat_name);

G_taxonomy = digraph;
m = size(relations,1);
%G_taxonomy=addedge(G_taxonomy,'information_systems','information_retrieval');
for i=1:m
    nc = relations(i,1)
    bc = relations(i,2)
    G_taxonomy=addedge(G_taxonomy,bc,nc);
end
%plot(G_taxonomy,'Layout','force','LineWidth',1);
%G_taxonomy = rmnode(G_taxonomy,8);

h = plot(G_taxonomy,'Layout','force','NodeLabel',[]);
length = size(G_taxonomy.Nodes,1);
for i=1:length
   text(h.XData(i)-0.1,h.YData(i)+0.1,G_taxonomy.Nodes.Name{i},'fontsize',12,'Interpreter','none');
end
highlight(h,1);
axis off

