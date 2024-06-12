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
               <% }
               if(request.getAttribute("etatStock") != null) {
               EtatStock etatStock = (EtatStock)request.getAttribute("etatStock");
               %>
                <h4 class="card-title">Etat de stock</h4>
                
                <div class="row">
                    <div class="col-lg-12 grid-margin strech-card">
                        <p style="margin-left:15%;  width:70%;">Date debut : <%=etatStock.getDatedebut() %></p>
                        <p style="margin-left:15%;  width:70%;">Date fin : <%=etatStock.getDateFin() %></p>
                        <p style="margin-left:15%;  width:70%;">Montant total : <%=Util.formatMonetaire(etatStock.getMontantTotal()) %> ariary</p>
                    </div>
                </div>
               
                    <table class="table table-striped">
                        <thead>
                            <tr style="background-color: gray">
                                <td>Code article</td>
                                <td>Designation</td>
                                <td>Unite</td>
                                <td>Magasin</td>
                                <td>Stock initial</td>
                                <td>Entree</td>
                                <td>Sortie</td>
                                <td>Stock final</td>
                                <td>Prix unitaire</td>
                                <td>Valeur stock</td>
                            </tr>
                        </thead>
                      <tbody>
                        <% for(int i = 0; i < etatStock.getStocks().size(); i++) { %>  
                        <tr>
                            <td><%=etatStock.getStocks().get(i).getCodeArticle() %></td>
                            <td><%=etatStock.getStocks().get(i).getArticle() %></td>
                            <td><%=etatStock.getStocks().get(i).getUnity() %></td>
                            <td><%=etatStock.getStocks().get(i).getStore() %></td>
                            <td><%=etatStock.getStocks().get(i).getStockInitial() %></td>
                            <td><%=etatStock.getStocks().get(i).getIn() %></td>
                            <td><%=etatStock.getStocks().get(i).getOut() %></td>
                            <td style="color:<%=etatStock.getStocks().get(i).getColorStatus() %>"><%=Util.formattedNumber(etatStock.getStocks().get(i).getStockFinal()) %></td>
                            <td><%=Util.formatMonetaire(etatStock.getStocks().get(i).getPrixUnitaire()) %></td>
                            <td><%=Util.formatMonetaire(etatStock.getStocks().get(i).getStockValue()) %></td>
                        </tr>
                        <% } %>
                        
                      </tbody>
                    </table>
                <% } %>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>