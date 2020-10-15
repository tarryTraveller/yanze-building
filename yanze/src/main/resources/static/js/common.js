// $(function(){
// 	//SpringSecurity的csrf设置
//     var token = $("meta[name='_csrf']").attr("content");
//     var header = $("meta[name='_csrf_header']").attr("content");
//     if (header && token) {
//         $(document).ajaxSend(function(e, xhr, options) {
//             xhr.setRequestHeader(header, token);
//         });
//     }
// });


/**
 * 时间格式化
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

/**
 * form表单序列化，组成json的key:value格式
 */
jQuery.prototype.serializeObject = function() {
	var obj = new Object();
	$.each(this.serializeArray(), function(index, param) {
		if (!(param.name in obj)) {
			obj[param.name] = param.value;
		}
	});
	return obj;
};

var BLOG = {
	formatDate : function(date, pattern) {
		if (typeof date == 'undefined' || date == undefined || date == null
				|| typeof date != 'object') {
			date = new Date();
		}
		if (typeof pattern == 'undefined' || pattern == undefined
				|| pattern == null || typeof pattern != 'string') {
			pattern = "yyyy-MM-dd hh:mm:ss";
		}
		return date.format(pattern);
	},
	formatDateByLong : function(l, pattern) {
		return this.formatDate(new Date(l - 0), pattern);
	},
	checkPost: function(str) { // 验证邮编
        var is = false;
        var re = /^[1-9]\d{5}$/;
        if (re.test(str)) {
            is = true;
        } else {
            is = false;
        }
        return is;
    },
    checkPhones: function(str) { // 验证电话（包括手机号和座机）
        return this.checkMobile(str) || this.checkPhone(str);
    },
    checkMobile: function(str) { // 验证手机号
        var is = false;
        var re = /^1\d{10}$/;
        if (re.test(str)) {
            is = true;
        } else {
            is = false;
        }
        return is;
    },
    checkPhone: function(str) { // 验证座机电话
        var is = false;
        var re = /^0\d{2,3}-?\d{7,8}$/;
        if (re.test(str)) {
            is = true;
        } else {
            is = false;
        }
        return is;
    },
    checkEmail: function(str) { // 验证邮箱
        var is = false;
        var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
        if (re.test(str)) {
            is = true;
        } else {
            is = false;
        }
        return is;
    },
    checkPassword: function(str){ //验证密码--以字母开头，长度在6-18之间，只能包含字母、数字和下划线
    	var is = false;
    	var re = /^[a-zA-Z]\w{5,17}$/;
    	if (re.test(str)) {
            is = true;
        } else {
            is = false;
        }
        return is;
    },
    isNull: function(str) {//判断是否为空，此方法0不为空，返回false
        if (typeof str == 'undefined' || str == undefined || str == null || str === '') {
            return true;
        }
        return false;
    },
    isNotNull: function(str) {//判断是否不为空，此方法0不为空，返回true
        if (typeof str != 'undefined' && str != undefined && str != null && str !== '') {
            return true;
        }
        return false;
    },
    removeNUll: function(d){  //去null
    	if(d == null || d == undefined){
    		return "";
    	}
    	else{
    		return d;
    	}
    }
}