<%@ page language="java"
	import="java.util.*,com.tianle.service.organization.*,com.tianle.model.base.*"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>后台管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-theme.min.css">
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$(".modal-body button").click(function() {
			$(this).toggleClass("btn-default btn-primary active");
		});

		$("#selectOrg").click(function() {
			fatherOrg = $(".active");
			a = fatherOrg.size();
			if(a>1) {
				alert("请只选择一个上级组织");
			} else {
				fatherOrgUUID = fatherOrg[0].id;
				fatherOrgName = fatherOrg[0].innerHTML;
				var temp = "<input type='hidden' name='fatheruuid' value='" + fatherOrgUUID + "' /><div class='panel panel-info w180'><div class='panel-body'>"+fatherOrgName+" </div> </div>";
				$('#selectButton').after(temp);
				$('#myModal').modal('hide');
			}
		});
	});
</script>
<body>
	<h2>后台管理</h2>
	<br>
	<form action="Login" method="post">
		<input type="text" value="hello" name="name" /> <input type="submit" />
	</form>
	<hr>
	<div class="classform">
		<form action="AddSth" method="post">
			<div class="form-group">
				<label>年级：</label> <input class="form-control" type="text"
					name="grade" placeholder="年级">
			</div>
			<div class="form-group">
				<label> 学院：</label> <input class="form-control" type="text"
					name="college" placeholder="学院">
			</div>
			<div class="form-group">
				<label>班级：</label> <input class="form-control" type="text"
					name="classname" placeholder="班级">
			</div>
			<input type="hidden" name="type" value="addclass"> <input
				class="btn btn-default" type="submit">
		</form>
	</div>


	<div class="orgform">
		<form action="AddSth" method="post">
			<div class="form-group">
				<label>组织名称</label> <input type="text" class="form-control"
					name="orgname" placeholder="组织名">
			</div>


			<div class="form-group">
				<label>上级组织</label> <br />
				<!-- Button trigger modal -->
				<button type="button" class="btn btn-primary btn-lg" id='selectButton'
					data-toggle="modal" data-target="#myModal">选择组织</button>

				<!-- Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">请选择上级组织</h4>
							</div>
							<div class="modal-body">

								<%
									OrgService os = new OrgService();
									ArrayList<Org> orgs = os.selectOrg();
									for (Org o : orgs) {
										out.println("<button type='button' name='org' class='btn btn-default' id='"
												+ o.getOrgUUID() + "'>" + o.getOrgName() + "</button>");
									}
								%>

							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button type="button" class="btn btn-primary" id="selectOrg">确定</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<input type="hidden" name="type" value="addorg">
			<button type="submit" class="btn btn-default">提交</button>
		</form>
	</div>
	<hr>
	
	<div class='addCircle'>
	
	</div>

</body>
</html>








