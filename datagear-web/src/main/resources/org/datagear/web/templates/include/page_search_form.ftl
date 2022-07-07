<#--
 *
 * Copyright 2018 datagear.tech
 *
 * Licensed under the LGPLv3 license:
 * http://www.gnu.org/licenses/lgpl-3.0.html
 *
-->
<#--
搜索表单。

-->
<form @submit.prevent="onSearchFormSubmit" class="py-1">
	<div class="p-inputgroup">
		<p-inputtext type="text" v-model="pm.searchForm.keyword"></p-inputtext>
		<p-button type="submit" icon="pi pi-search" />
	</div>
</form>
<script>
(function(po)
{
	po.vuePageModel(
	{
		searchForm: { keyword: "" }
	});
	
	po.vueMethod(
	{
		onSearchFormSubmit: function()
		{
			var param = po.vueRaw(po.vuePageModel().searchForm);
			po.search(param);
		}
	});
	
	po.search = function(formData){};
})
(${pid});
</script>