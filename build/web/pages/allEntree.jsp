<%@page import="model.mouvement.In"%>
<%@page import="java.util.List"%>
<%@page import="utilitaire.Util"%>
<%@page import="model.local.Store"%>
<%@page import="model.produit.Article"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.suiviStock.EtatStock"%>
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
                <h4 class="card-title">Liste des entrees</h4>
                
                <div class="row">
                    <div class="col-lg-12 grid-margin strech-card">
                        <a href="SaisieIn">
                            <button class="btn btn-gradient-primary me-2">Nouvelle entree</button>
                        </a>
                    </div>
                </div>
               
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <td>ID</td>
                                <td>Date</td>
                                <td>Article</td>
                                <td>Magasin</td>
                                <td>Unite</td>
                                <td>Unite equivalence</td>
                                <td>Qte unitaire</td>
                                <td>Qte equiv</td>
                                <td>Prix unitaire</td>
                                <td>Prix saisie</td>
                                <td>Etat</td>
                                <td>Action</td>
                            </tr>
                        </thead>
                      <tbody>
                        <% if(request.getAttribute("ins") != null) { 
                        List<In> ins = (List<In>)request.getAttribute("ins");
                        for(int i = 0; i < ins.size(); i++) { %>  
                        <tr>
                            <td><%=ins.get(i).getIdIn() %></td>
                            <td><%=ins.get(i).getDateIn() %></td>
                            <td><%=ins.get(i).getArticle().getName() %></td>
                            <td><%=ins.get(i).getStore().getName() %></td>
                            <td><%=ins.get(i).getArticle().getUnity().getNom() %></td>
                            <td><%=ins.get(i).getUnityEquivalent().getDesignation() %></td>
                            <td><%=Util.formattedNumber(ins.get(i).getQuantity()) %></td>
                            <td><%=Util.formattedNumber(ins.get(i).getQuantityReel()) %></td>
                            <td><%=Util.formatMonetaire(ins.get(i).getUnitary_cost()) %> ARIARY</td>
                            <td><%=Util.formatMonetaire(ins.get(i).getPrixReel()) %> ARIARY</td>
                            <td><label class="badge badge-<%=ins.get(i).getStatusBadge() %>"><%=ins.get(i).getStatusLetter() %></label></td>
                            <td><a href="DetailsMvt?idIn=<%=ins.get(i).getIdIn() %>&idHelp=1">Details</a></td>
                        </tr>
                        <% } } %>
                      </tbody>
                    </table>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>