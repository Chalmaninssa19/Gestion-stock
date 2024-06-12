<%@page import="model.produit.UnityEquivalent"%>
<%@page import="model.mouvement.RequestOut"%>
<%@page import="model.local.Store"%>
<%@page import="model.produit.Article"%>
<%@page import="model.mouvement.Out"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-md-6 grid-margin stretch-card">
          <div class="card">
               <div class="card-body">
                   <% if(request.getAttribute("requestOut") != null) { 
                   RequestOut outed = (RequestOut)request.getAttribute("requestOut");
                   %>
                    <h4 class="card-title">Details demande sortie</h4>
                    <form class="forms-sample" action="EditMvt" method="POST">
                        <div class="form-group">
                            <label for="exampleSelectGender">Article</label>
                            <select class="form-control" id="exampleSelectGender" name="article">
                                <% if(request.getAttribute("articles") != null) { 
                                ArrayList<Article> articles = (ArrayList<Article>)request.getAttribute("articles");
                                for(int i = 0; i < articles.size(); i++) { 
                                if(outed.getArticle().getIdArticle() == articles.get(i).getIdArticle()) {
                                %>
                                    <option selected value="<%=articles.get(i).getIdArticle() %>"><%=articles.get(i).getName() %></option>
                                <% } else { %>
                                    <option value="<%=articles.get(i).getIdArticle() %>"><%=articles.get(i).getName() %></option>
                            <% } } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleSelectGender">Magasin</label>
                            <select class="form-control" id="exampleSelectGender" name="store">
                                <% if(request.getAttribute("stores") != null) { 
                                ArrayList<Store> stores = (ArrayList<Store>)request.getAttribute("stores");
                                for(int i = 0; i < stores.size(); i++) { 
                                if(outed.getStore().getIdStore() == stores.get(i).getIdStore()) {
                                %>
                                    <option selected value="<%=stores.get(i).getIdStore() %>"><%=stores.get(i).getName() +" - "+stores.get(i).getAdresse().getAdresse() %></option>
                                <% } else { %>
                                    <option value="<%=stores.get(i).getIdStore() %>"><%=stores.get(i).getName() +" - "+stores.get(i).getAdresse().getAdresse() %></option>
                                <% } } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleSelectGender">Unite equivalence</label>
                            <select class="form-control" id="exampleSelectGender" name="unityEquivalent">
                                <% if(request.getAttribute("unityEquivalents") != null) { 
                                ArrayList<UnityEquivalent> unityEquivalents = (ArrayList<UnityEquivalent>)request.getAttribute("unityEquivalents");
                                for(int i = 0; i < unityEquivalents.size(); i++) { 
                                if(outed.getUnityEquivalent().getIdUnityEquivalent()==unityEquivalents.get(i).getIdUnityEquivalent()) { %>
                                    <option selected value="<%=unityEquivalents.get(i).getIdUnityEquivalent() %>"><%=unityEquivalents.get(i).getDesignation() %></option>
                                <% } else { %>
                                    <option value="<%=unityEquivalents.get(i).getIdUnityEquivalent() %>"><%=unityEquivalents.get(i).getDesignation() %></option>
                                <% } } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date" value="<%=outed.getDateRequestOut() %>">
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="idRequestOut" value="<%=outed.getIdRequestOut() %>">
                            <input type="hidden" name="idType" value="2">
                            <label for="exampleInputUsername1">Quantite</label>
                            <input type="number" step="0.01" class="form-control" id="exampleInputUsername1" name="quantity" value="<%=outed.getQuantity() %>">
                        </div>
                        <% if(outed.getStatus() == 1) { %> 
                        <button type="submit" class="btn btn-gradient-primary me-2">Modifier</button>
                        <% } %>
                    </form>
                    <br>
                    <% if(outed.getStatus() == 1) { %> 
                    <p>
                        <a href="TraitMvt?idOut=<%=outed.getIdRequestOut() %>&idType=2&status=0">
                            <button type="button" class="btn btn-gradient-danger btn-fw">Refuser</button>
                        </a>
                        <a href="TraitMvt?idOut=<%=outed.getIdRequestOut() %>&idType=2&status=10">
                            <button type="button" class="btn btn-gradient-success btn-fw">Valider</button>
                        </a>
                    </p>
                    <% } %>
                    <p>
                         <a href="AllOut">
                            <button type="button" class="btn btn-outline-dark btn-fw">retour</button>
                        </a>
                    </p>
                    
                <% } if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-success" role="alert" style="color: red">
                        <%=request.getAttribute("error") %>
                    </div>
                <% } %>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>