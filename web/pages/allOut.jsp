<%@page import="model.mouvement.RequestOut"%>
<%@page import="model.mouvement.Out"%>
<%@page import="model.mouvement.In"%>
<%@page import="java.util.List"%>
<%@page import="utilitaire.Util"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-lg-6 grid-margin stretch-card">
          <div class="card">
               <div class="card-body">
               <%
                    if(request.getAttribute("error")!=null){
                    %>
                    <div class="alert alert-success" role="alert">
                         <%=request.getAttribute("error")%>
                    </div>
               <% } %>
                <h4 class="card-title">Liste des demandes sorties</h4>
                
                <div class="row">
                    <div class="col-lg-12 grid-margin strech-card">
                        <a href="SaisieOut">
                            <button class="btn btn-gradient-primary me-2">Nouvelle demande sortie</button>
                        </a>
                    </div>
                </div>
               
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <td>ID</td>
                                <td>Derniere modification</td>
                                <td>Article</td>
                                <td>Magasin</td>
                                <td>Unite</td>
                                <td>Unite equiv</td>
                                <td>Qte unitaire</td>
                                <td>Qte saisie</td>
                                <td>Etat</td>
                                <td>Action</td>
                            </tr>
                        </thead>
                      <tbody>
                        <% if(request.getAttribute("requestOuts") != null) { 
                        List<RequestOut> requestOuts = (List<RequestOut>)request.getAttribute("requestOuts");
                        for(int i = 0; i < requestOuts.size(); i++) { %>  
                        <tr>
                            <td><%=requestOuts.get(i).getIdRequestOut() %></td>
                            <td><%=requestOuts.get(i).getDateRequestOut() %></td>
                            <td><%=requestOuts.get(i).getArticle().getName() %></td>
                            <td><%=requestOuts.get(i).getStore().getName() %></td>
                            <td><%=requestOuts.get(i).getArticle().getUnity().getNom() %></td>
                            <td><%=requestOuts.get(i).getUnityEquivalent().getDesignation() %></td>
                            <td><%=Util.formattedNumber(requestOuts.get(i).getQuantity()) %></td>
                            <td><%=Util.formattedNumber(requestOuts.get(i).getQuantityReel()) %></td>
                            <td><label class="badge badge-<%=requestOuts.get(i).getStatusBadge() %>"><%=requestOuts.get(i).getStatusLetter() %></label></td>
                            <td><a href="DetailsMvt?idRequestOut=<%=requestOuts.get(i).getIdRequestOut() %>&idHelp=2">Details</a></td>
                        </tr>
                        <% } } %>
                      </tbody>
                    </table>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>