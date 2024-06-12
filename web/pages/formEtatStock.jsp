<%@page import="model.local.Store"%>
<%@page import="model.produit.Article"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-md-6 grid-margin stretch-card">
          <div class="card">
               <div class="card-body">
                    <h4 class="card-title">Mon etat de stock</h4>
                    <form class="forms-sample" action="EtatStock" method="POST">
                        <div class="form-group">
                            <label for="exampleSelectGender">Article</label>
                            <select class="form-control" id="exampleSelectGender" name="article">
                                <% if(request.getAttribute("articles") != null) { 
                                ArrayList<Article> articles = (ArrayList<Article>)request.getAttribute("articles"); %>
                                <option value="%">All</option>
                                <% for(int i = 0; i < articles.size(); i++) { %>
                                    <option value="<%=articles.get(i).getIdArticle() %>"><%=articles.get(i).getName() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleSelectGender">Magasin</label>
                            <select class="form-control" id="exampleSelectGender" name="store">
                                <% if(request.getAttribute("stores") != null) { 
                                ArrayList<Store> stores = (ArrayList<Store>)request.getAttribute("stores"); %>
                                <option value="%">All</option>
                                <% for(int i = 0; i < stores.size(); i++) { %>
                                <option value="<%=stores.get(i).getIdStore() %>"><%=stores.get(i).getName() +" - "+stores.get(i).getAdresse().getAdresse() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date debut</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date_debut">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date fin</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date_fin">
                        </div>
                       
                        <button type="submit" class="btn btn-gradient-primary me-2">Afficher</button>
                    </form>
                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-success" role="alert" style="color: red">
                        <%=request.getAttribute("error") %>
                    </div>
                <% } %>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>