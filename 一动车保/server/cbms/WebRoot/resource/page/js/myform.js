//跳转分页
function topage(page) {
	var form = document.getElementById("pageList");
	$("#inputPage").val(page);
	/*console.log(form);
	form.page.value = page;*/
	form.submit();
}

// 跳转到指定页面
function go(totalPage) {
	var inputPageValue = document.getElementById("inputPage").value;
	if (inputPageValue > totalPage) {
		alert("超过最大页数: " + totalPage);
	} else if (inputPageValue < 1) {
		alert("页码数必须大于等于1");
	} else {
		var form = document.getElementById("pageList");
		//form.page.value = inputPageValue;
		form.submit();
	}
}
// 设置页码为1
function confirmQuery() {
	var form = document.getElementById("pageList");
	//form.page.value = 1;
	$("#inputPage").val("1");
	form.submit();
}


$(document).ready(function(){
	
	var $deleteButton = $("#deleteButton");
	var $refreshButton = $("#refreshButton");
	var $listTable = $("#listTable");
	var $selectAll = $("#selectAll");
	var $ids = $("#listTable input[name='ids']");
	var $contentRow = $("#listTable tr:gt(0)");
	var $add = $("#add");
	//删除
	$deleteButton.click( function() {
		var $this = $(this);
		if ($this.hasClass("disabled")) {
			return false;
		}
		var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
		if (confirm("您确定要删除吗？") == true) {
			$.ajax({
				url: $(this).attr("val")+"/delete",
				type: "POST",
				data: $checkedIds.serialize(),
				dataType: "json",
				cache: false,
				success: function(data) {
					if (data.status == "success") {
						alert(data.message);
						$checkedIds.closest("tr").remove();
						if ($listTable.find("tr").size() <= 1) {
							setTimeout(function() {
								location.reload(true);
							}, 3000);
						}
					}else{
						alert(data.message);
					}
				}
			});
		}
	});
	
	// 刷新
	$refreshButton.click( function() {
		location.reload(true);
		return false;
	});
	
	// 全选
	$selectAll.click( function() {
		var $this = $(this);
		var $enabledIds = $("#listTable input[name='ids']:enabled");
		if ($this.prop("checked")) {
			$enabledIds.prop("checked", true);
			if ($enabledIds.filter(":checked").size() > 0) {
				//$deleteButton.removeClass("disabled");
				$deleteButton.removeAttr("disabled");

				$contentRow.addClass("selected");
			} else {
				//$deleteButton.addClass("disabled");
				$deleteButton.attr("disabled", true)
			}
		} else {
			$enabledIds.prop("checked", false);
			//$deleteButton.addClass("disabled");
			$deleteButton.attr("disabled", true)
			$contentRow.removeClass("selected");
		}
	});
	
	// 选择
	$ids.click( function() {
		var $this = $(this);
		if ($this.prop("checked")) {
			$this.closest("tr").addClass("selected");
			//$deleteButton.removeClass("disabled");
			$deleteButton.removeAttr("disabled");
		} else {
			$this.closest("tr").removeClass("selected");
			if ($("#listTable input[name='ids']:enabled:checked").size() > 0) {
				//$deleteButton.removeClass("disabled");
				$deleteButton.removeAttr("disabled");
			} else {
				//$deleteButton.addClass("disabled");
				$deleteButton.attr("disabled", true);
			}
		}
	});
	
	$add.click(function(){
		var $this = $(this);
		var baseUrl = $this.attr("val");
		window.location.href= baseUrl + "/add";
	});
});
