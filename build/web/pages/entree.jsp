<%@page import="model.produit.UnityEquivalent"%>
<%@page import="model.local.Store"%>
<%@page import="model.produit.Article"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-md-6 grid-margin stretch-card">
          <div class="card">
               <div class="card-body">
                    <h4 class="card-title">Saisie d'entree de stock</h4>
                    <form class="forms-sample" action="SaisieIn" method="POST">
                        <div class="form-group">
                            <label for="exampleSelectGender">Article</label>
                            <select class="form-control" id="exampleSelectGender" name="article">
                                <% if(request.getAttribute("articles") != null) { 
                                ArrayList<Article> articles = (ArrayList<Article>)request.getAttribute("articles");
                                for(int i = 0; i < articles.size(); i++) { %>
                                    <option value="<%=articles.get(i).getIdArticle() %>"><%=articles.get(i).getName() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleSelectGender">Magasin</label>
                            <select class="form-control" id="exampleSelectGender" name="store">
                                <% if(request.getAttribute("stores") != null) { 
                                ArrayList<Store> stores = (ArrayList<Store>)request.getAttribute("stores");
                                for(int i = 0; i < stores.size(); i++) { %>
                                <option value="<%=stores.get(i).getIdStore() %>"><%=stores.get(i).getName() +" - "+stores.get(i).getAdresse().getAdresse() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleSelectGender">Unite equivalence</label>
                            <select class="form-control" id="exampleSelectGender" name="unityEquivalent">
                                <% if(request.getAttribute("unityEquivalents") != null) { 
                                ArrayList<UnityEquivalent> unityEquivalents = (ArrayList<UnityEquivalent>)request.getAttribute("unityEquivalents");
                                for(int i = 0; i < unityEquivalents.size(); i++) { %>
                                <option value="<%=unityEquivalents.get(i).getIdUnityEquivalent() %>"><%=unityEquivalents.get(i).getDesignation() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Quantite</label>
                            <input type="number" step="0.01" class="form-control" id="exampleInputUsername1" name="quantity">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Cout unitaire</label>
                            <input type="number" step="0.01" class="form-control" id="exampleInputUsername1" name="coutUnitaire">
                        </div>
                       
                        <button type="submit" class="btn btn-gradient-primary me-2">Entrer</button>
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