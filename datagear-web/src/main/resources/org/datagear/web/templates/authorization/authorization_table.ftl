<#--
 *
 * Copyright 2018 datagear.tech
 *
 * Licensed under the LGPLv3 license:
 * http://www.gnu.org/licenses/lgpl-3.0.html
 *
-->
<#include "../include/page_import.ftl">
<#include "../include/html_doctype.ftl">
<html>
<head>
<#include "../include/html_head.ftl">
<title>
	<#include "../include/html_app_name_prefix.ftl">
	<@spring.message code='${resourceMeta.authModuleLabel}' />
</title>
</head>
<body class="p-card no-border">
<#include "../include/page_obj.ftl">
<div id="${pid}" class="page page-manager page-table">
	<div class="page-header grid align-items-center">
		<div class="col-12 md:col-3">
			<#include "../include/page_search_form.ftl">
		</div>
		<div class="h-opts col-12 md:col-9 text-right">
			<p-button label="<@spring.message code='confirm' />" @click="onSelect" v-if="isSelectAction"></p-button>
			
			<p-button label="<@spring.message code='add' />" @click="onAdd" v-if="!isSelectAction"></p-button>
			<p-button label="<@spring.message code='edit' />" @click="onEdit" v-if="!isSelectAction"></p-button>
			<p-button label="<@spring.message code='view' />" @click="onView"></p-button>
			<p-button label="<@spring.message code='delete' />" @click="onDelete" class="p-button-danger" v-if="!isSelectAction"></p-button>
		</div>
	</div>
	<div class="page-content">
		<p-datatable :value="pm.items" :scrollable="true" scroll-height="flex"
			:loading="pm.loading" :lazy="true"
			sort-mode="multiple" :multi-sort-meta="pm.multiSortMeta" @sort="onSort($event)"
			v-model:selection="pm.selectedItems" :selection-mode="pm.selectionMode" dataKey="id" striped-rows>
			<p-column :selection-mode="pm.selectionMode" :frozen="true" class="col-check"></p-column>
			<p-column field="id" header="<@spring.message code='id' />" :hidden="true"></p-column>
			<p-column field="principalName" header="<@spring.message code='${resourceMeta.authPrincipalLabel}' />"
				:sortable="true">
			</p-column>
			<p-column field="permissionLabel" header="<@spring.message code='${resourceMeta.authPermissionLabel}' />"
				:sortable="true" :hidden="singlePermission">
			</p-column>
			<p-column field="enabled" header="<@spring.message code='${resourceMeta.authEnabledLabel}' />"
				:sortable="true" :hidden="!enableSetEnable">
				<template #body="{data}">
					{{formatEnabled(data)}}
				</template>
			</p-column>
		</p-datatable>
	</div>
</div>
<#include "../include/page_manager.ftl">
<#include "../include/page_table.ftl">
<script>
(function(po)
{
	po.enableSetEnable = ("${resourceMeta.enableSetEnable?string('true', 'false')}" == "true");
	po.singlePermission = ("${resourceMeta.singlePermission?string('true', 'false')}"  == "true");
	
	po.url = function(action)
	{
		return "/authorization/${resourceMeta.resourceType}/" + encodeURIComponent("${resource?js_string?no_esc}") + "/" + action;
	};
	
	po.vueRef("enableSetEnable", po.enableSetEnable);
	po.vueRef("singlePermission", po.singlePermission);
	
	po.setupAjaxTable(po.url("queryData"),
	{
		multiSortMeta: [ {field: "principalName", order: 1} ]
	});
	
	po.vueMethod(
	{
		formatEnabled: function(data)
		{
			return (data.enabled ? "<@spring.message code='true' />" : "<@spring.message code='false' />");
		},
		onAdd: function()
		{
			po.handleAddAction(po.url("add"));
		},
		
		onEdit: function()
		{
			po.handleOpenOfAction(po.url("edit"));
		},
		
		onView: function()
		{
			po.handleOpenOfAction(po.url("view"));
		},
		
		onDelete: function()
		{
			po.handleDeleteAction(po.url("delete"));
		},
		
		onSelect: function()
		{
			po.handleSelectAction();
		}
	});
	
	po.vueMount();
})
(${pid});
</script>
</body>
</html>