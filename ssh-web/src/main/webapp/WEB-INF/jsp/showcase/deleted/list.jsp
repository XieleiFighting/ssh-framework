<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">


    <ul class="nav nav-tabs">
        <li ${param['search.deleted_eq'] ne 'TRUE' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/deleted">
                <i class="icon-table"></i>
                所有示例列表
            </a>
        </li>
        <li ${param['search.deleted_eq'] eq 'TRUE' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/deleted?search.deleted_eq=TRUE">
                <i class="icon-table"></i>
                已删除的示例列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="showcase:deleted:create">
                <a class="btn btn-create" href="${ctx}/showcase/deleted/create">
                    <span class="icon-file-alt"></span>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:deleted:update">
                <a class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:deleted:delete">
                <a class="btn btn-delete">
                    <span class="icon-trash"></span>
                    删除
                </a>
                </shiro:hasPermission>
            </div>
        </div>
        <div class="span8">
            <%@include file="searchForm.jsp"%>
        </div>
    </div>
    <%@include file="listTable.jsp"%>
</div>
<es:contentFooter/>
