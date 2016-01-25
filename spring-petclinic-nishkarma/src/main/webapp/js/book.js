$(function() {

	$.extend($.jgrid.defaults, {
				datatype: 'json',
				jsonReader : {
					repeatitems:false,
					total: function(result) {
						//Total number of pages
						return Math.ceil(result.total / result.pageSize);
					},
					records: function(result) {
						//Total number of records
						return result.total;
					}
				},
				prmNames: {
					page: "page_page",
					rows: "page_size",
					sort: "page_sort",
					order: "page_sort_dir"
				},
				sortname: 'title',
				sortorder: 'asc',
				height: 'auto',
				viewrecords: true,
				rowList: [10, 20, 50, 100],
				altRows: true,
				loadError: function(jqXHR, textStatus, errorThrown) {

					var errorDetail = $.parseJSON(jqXHR.responseText);
					var errorMsg = "";
					$.each(errorDetail.errorMap.entry, function( index ) {
						errorMsg +=  "<p>"+ this.key + " : " + this.value;
					});

					$('#' + this.id + '_err').remove();

				    // insert div with the error description before the grid
				    $(this).closest('div.ui-jqgrid').before(
				        '<div id="' + this.id + '_err" style="max-width:' + this.style.width +
				            ';"><div class="ui-state-error ui-corner-all" style="padding:.3em;float:left;">' +
				            	errorDetail.status + "(" + jqXHR.status + ")" + errorMsg +
				            '</div><div style="clear:left"/></div>'
				    );
				    
				},
				
			});

	$.extend($.jgrid.edit, {
				closeAfterEdit: true,
				closeAfterAdd: true,
				ajaxEditOptions: { contentType: "application/json" },
				mtype: 'POST',
				serializeEditData: function(data) {
					delete data.oper;
					return JSON.stringify(data);
				},
				errorTextFormat: function (response) {
					var xmlDoc = $.parseXML(response.responseText);

					var errorMsg = "";
					
					$(xmlDoc).find("entry").each(function() {
					    errorMsg +=  "<p>"+ $(this).find("key").text() + " : " + $(this).find("value").text();
					 });
					
				    return '<span class="ui-icon ui-icon-alert" ' +
				                 'style="float:left; margin-right:.3em;"></span>' +
				                 $(xmlDoc).find("status").text() + "(" + response.status + ")" + errorMsg;
				},				
			});
	$.extend($.jgrid.del, {
				mtype: 'DELETE',
				serializeDelData: function() {
					return "";
				},
				errorTextFormat: function (response) {
					var xmlDoc = $.parseXML(response.responseText);

					var errorMsg = "";
					
					$(xmlDoc).find("entry").each(function() {
					    errorMsg +=  "<p>"+ $(this).find("key").text() + " : " + $(this).find("value").text();
					 });
					
				    return '<span class="ui-icon ui-icon-alert" ' +
				                 'style="float:left; margin-right:.3em;"></span>' +
				                 $(xmlDoc).find("status").text() + "(" + response.status + ")" + errorMsg;
				},
			});

	var editOptions = {
		onclickSubmit: function(params, postdata) {
			params.url = URL + '/' + postdata.id;
		}
	};
	var addOptions = {	mtype: "PUT",
						errorTextFormat: function (response) {
							var xmlDoc = $.parseXML(response.responseText);
			
							var errorMsg = "";
							
							$(xmlDoc).find("entry").each(function() {
							    errorMsg +=  "<p>"+ $(this).find("key").text() + " : " + $(this).find("value").text();
							 });
							
						    return '<span class="ui-icon ui-icon-alert" ' +
						                 'style="float:left; margin-right:.3em;"></span>' +
						                 $(xmlDoc).find("status").text() + "(" + response.status + ")" + errorMsg;
						}
					};
	var delOptions = {
		onclickSubmit: function(params, postdata) {
			params.url = URL + '/' + postdata;
		}
	};

	var searchOptions = {search: false}; //disable for sql injaction
	
	var URL = '../restful/book';
	var options = {
		url: URL,
		editurl: URL,
		colModel:[
			{
				name:'id',
				label: 'ID',
				index: 'id',
				formatter:'integer',
				width: 40,
				editable: true,
				editoptions: {disabled: true, size:5}
			},
			{
				name:'title',
				label: 'Title',
				index: 'title',
				width: 300,
				editable: true,
				editrules: {required: true}
			},
			{
				name:'author',
				label: 'Author',
				index: 'author',
				width: 200,
				editable: true,
				search:false,
				editrules: {required: true}
			},
			{
				name:'cover',
				label: 'Cover',
				index: 'cover',
				hidden: false,
				editable: true,
				edittype: 'select',
				editrules: {edithidden:true},
				editoptions: {
					value: {'PAPERBACK': 'paperback', 'HARDCOVER': 'hardcover', 'DUST_JACKET': 'dust jacket'}
				}
			},
			{
				name:'publishedyear',
				label: 'Published year',
				index: 'publishedYear',
				width: 80,
				align: 'center',
				editable: true,
				editrules: {required: true, integer: true},
				editoptions: {size:5, maxlength: 4}
			},
			{
				name:'available',
				label: 'Available',
				index: 'available',
				formatter: 'checkbox',
				width: 46,
				align: 'center',
				editable: true,
				edittype: 'checkbox',
				editoptions: {value:"true:false"}
			},
			{
				name:'comments',
				label: 'Comments',
				index: 'comments',
				hidden: true,
				editable: true,
				edittype: 'textarea',
				editrules: {edithidden:true}
			}
		],
		caption: "Books",
		pager : '#pager',
		height: 'auto',
		ondblClickRow: function(id) {
			jQuery(this).jqGrid('editGridRow', id, editOptions);
		}
	};

	$("#grid")
			.jqGrid(options)
			.navGrid('#pager',
			searchOptions, //options
			editOptions,
			addOptions,
			delOptions,
			{}
	);

});
