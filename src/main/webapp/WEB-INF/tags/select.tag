<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="url" required="true"%>
<%@ attribute name="defaultVal" required="false"%>

<input class="easyui-combobox" 
	name="${name}" 
	value="${defaultVal}" 
	style="width:width:173px;"
	data-options="
		required:	true,
		loadFilter:	comboboxFilter,
		editable:	false,
		url:		'${url}',
		method:		'get',
		valueField:	'id',
		textField:	'name',
		panelHeight:'auto'
	">
<script>
</script>