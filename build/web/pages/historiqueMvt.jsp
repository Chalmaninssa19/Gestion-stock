<%@page import="utilitaire.Util"%>
<%@page import="model.mouvement.Out"%>
<%@page import="model.mouvement.In"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<div class="row">
    
    <div class="col-md-6 grid-margin stretch-card">
        <div class="card" style="border: 1px solid black">
            <div class="card-body">
                <h2 class="card-title" style="color: yellowgreen">Historique entrees</h2>
                <% if(request.getAttribute("ins") != null) { 
                List<In> ins = (List<In>)request.getAttribute("ins");
                for(int i = 0; i < ins.size(); i++) { %>
                    <hr>
                    <h4 class="card-title"><span class="font-weight-bold"><%=ins.get(i).getDateIn() %></span></h4>
                    <p> Entree de <%=Util.formattedNumber(ins.get(i).getQuantity()) %> <%=ins.get(i).getArticle().getUnity().getNom() %> de <span class="font-weight-bold"><%=ins.get(i).getArticle().getName() %> <%=ins.get(i).getArticle().getCode() %></span> dans <%=ins.get(i).getStore().getName() %> avec <%=Util.formatMonetaire(ins.get(i).getUnitary_cost()) %> ariary le cout unitaire</p>
                    <% if(ins.get(i).getStatus() == 1) { %>
                    <a href="refuseMvt?idOut=<%=ins.get(i).getIdIn() %>&idType=1&status=0">
                        <button type="button" class="btn btn-gradient-success btn-fw">Refuser</button>
                    </a>
                    <a href="valideMvt?idOut=<%=ins.get(i).getIdIn() %>&idType=1&status=10">
                        <button type="button" class="btn btn-gradient-danger btn-fw">Valider</button>
                    </a>
                <% } else if(ins.get(i).getStatus() == 10) { %>
                        <label class="badge badge-success">Valide</label>
                    <% } else if(ins.get(i).getStatus() == 0) { %>
                        <label class="badge badge-danger">Refuse</label>
                    <% } } } %>
            </div>
        </div>
    </div>
        
    <div class="col-md-6 grid-margin stretch-card">
        <div class="card" style="border: 1px solid black">
            <div class="card-body">
                <h2 class="card-title" style="color: yellowgreen">Historique sorties</h2>
                <% if(request.getAttribute("ins") != null) { 
                    List<Out> outs = (List<Out>)request.getAttribute("outs");
                    for(int i = 0; i < outs.size(); i++) { %>
                    <hr>
                    <h4 class="card-title"><span class="font-weight-bold"><%=outs.get(i).getDateOut() %></span></h4>
                    <p> Sortie de <%=Util.formattedNumber(outs.get(i).getQuantity()) %> <%=outs.get(i).getArticle().getUnity().getNom() %> de <span class="font-weight-bold"><%=outs.get(i).getArticle().getName() %> <%=outs.get(i).getArticle().getCode() %></span> dans <%=outs.get(i).getStore().getName() %></p>
                    <% if(outs.get(i).getStatus() == 1) { %>
                    <a href="refuseMvt?idOut=<%=outs.get(i).getIdOut() %>&idType=2&status=0">
                        <button type="button" class="btn btn-gradient-success btn-fw">Refuser</button>
                    </a>
                        <a href="valideMvt?idOut=<%=outs.get(i).getIdOut() %>&idType=2&status=10">
                        <button type="button" class="btn btn-gradient-danger btn-fw">Valider</button>
                    </a>
                    <% } else if(outs.get(i).getStatus() == 10) { %>
                        <label class="badge badge-success">Valide</label>
                    <% } else if(outs.get(i).getStatus() == 0) { %>
                        <label class="badge badge-danger">Refuse</label>
                    <% } } } %>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>