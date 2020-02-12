$(document).ready(function() {
	console.log(customreport);
	var bindField = new BindField();
	bindField.setoption('fieldname', 'a');
	customreport.addBindField(bindField);
	console.log(bindField.fieldname);
});
$(document).ready(
		function() {
			$('#datasettreeid').tree({
				url : "datasettree.jhtml",
				loadFilter : function(data) {
					return data;
				}
			});
			$('#ispaging').change(function() {
				var checked = $(this).is(':checked');
				customreport.setPaging(checked);
			});
			$('#isexport').change(function() {
				var checked = $(this).is(':checked');
				customreport.setExp(checked);
			});
			$('#isrequired').change(
					function() {
						var checked = $(this).is(':checked');
						// this.currentCondFieldName
						if (customreport.currentCondFieldName == '') {
							alert('请选择要设置的查询条件。');
							return;
						}
						customreport.setReportConditionProperty(
								customreport.currentCondFieldName, 'required',
								checked);
					});

		});
$(function() {
	$('#datasettreeid').tree({
		onExpand : function(node) {
			for (var i = 0; i < node.children.length; i++) {
				var nodei = node.children[i];
				if (nodei.attributes.isleaf) {
					$('#' + nodei.domId).draggable({
						revert : true,
						deltaX : 10,
						deltaY : 10,
						proxy : function(source) {
							var n = $('<div class="proxy"></div>');
							n.html($(source).html()).appendTo('body');
							return n;
						}
					})
				}
			}
		}
	});
});
$(function() {
	$('#selarea')
			.droppable(
					{
						accept : $('#datasettreeid').tree('getSelected'),
						onDragEnter : function(e, source) {
							$(this).addClass('over');
						},
						onDragLeave : function(e, source) {
							$(this).removeClass('over');
						},
						onDrop : function(e, source) {
							$(this).removeClass('over');
							if ($(source).hasClass('assigned')) {
								$(this).append(source);
							} else {
								var draggedNode = $('#datasettreeid').tree(
										'getNode', source);
								if (!customreport
										.isContainCondition(draggedNode.attributes.id)) {
									customreport.rendercomponent(draggedNode,
											this);
								}

							}
						}
					});
	$('#gridarea').droppable({
		accept : $('#datasettreeid').tree('getSelected'),
		onDragEnter : function(e, source) {
			$(this).addClass('over');
		},
		onDragLeave : function(e, source) {
			$(this).removeClass('over');
		},
		onDrop : function(e, source) {
			$(this).removeClass('over');
			if ($(source).hasClass('assigned')) {
				$(this).append(source);
			} else {
				var c = $(source).clone().addClass('assigned');
				$(this).empty().append(c);
				c.draggable({
					revert : true
				});
			}
		}
	});
});
