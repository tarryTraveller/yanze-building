$(function(){
	var spnUsername=$(".spn-username");
	var spnPassword=$(".spn-password");
	$("#btnCommit").click(function(){
		var params = $('#form_login').serializeObject();
		$.ajax({
			type:'POST',
			url:'/login/check', 
			data:params, 
			dataType:'json',
			success:function(data){
				if(data.status==200){//成功
					spnUsername.hide();
					spnPassword.hide();
					$("#form_login").submit();
				}else if(data.status==5000){//用户名不存在
					spnUsername.show();
					spnPassword.hide();
				}else if(data.status==5001){//密码错误
					spnUsername.hide();
					spnPassword.show();
				}
			}
		});
	});
});

