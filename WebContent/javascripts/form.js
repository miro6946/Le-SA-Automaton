(function($){
	
	$.fn.validate = function(){
			
		var inputs = $('input:not(:disabled), select:enabled, textarea:enabled', $(this));
		
		for(var i = 0, to = inputs.length ; i < to ; i++ ){
			
			var input = $(inputs[i]);
			
			if(input.is(':text, :password'))input.val($.trim(input.val()));
			
			var required = input.attr("presence") == 'presence';
			var minimum = input.attr("minimum");
			var maximum = input.attr("maximum");
			var option = input.attr("option");
			var confirm = input.attr("confirm");
			var glue = input.attr("glue");
			var pattern = input.attr("pattern");

			if(!input.is(':checkbox, :radio')){

				if(required && (input.val() == null || input.val() == ""))return doError(input, appMessage.NO_BLANK);

				minimum = parseInt(minimum);
				if (/^\d+$/.test(minimum)) {
					var temp = new String(input.val());
					if (temp.bytes() < minimum)return doError(input, appMessage.TOO_SHORT.replace("{n}", minimum));
				}

				maximum = parseInt(maximum);
				if (/^\d+$/.test(maximum)) {
					var temp = new String(input.val());
					if (temp.bytes() > maximum)return doError(input, appMessage.TOO_LONG.replace("{n}", maximum));
				}

				if (confirm && (input.val() != $(confirm, input.parents('form')).val())) return doError(input, appMessage.NOT_MATCH);

				if (option && input.val()) {

					if(option == 'Float' || option == 'Int'){
						
						try{

							var tempVal = eval('parse'+ option +'(eval(input.val()))');
							if(isRealNumber(tempVal)){
								try{
									var min = eval('parse'+ option +'(eval(input.attr("greater")))');
									if(isRealNumber(min) && min > tempVal)return doError(input, appMessage.GREATER_THEN.replace("{n}", min));
								}catch(e){/*do nothing*/}
								try{
									var max = eval('parse'+ option +'(eval(input.attr("lesser")))');
									if(isRealNumber(max) && max < tempVal)return doError(input, appMessage.LESSER_THEN.replace("{n}", max));
								}catch(e){/*do nothing*/}
							}else{
								throw "Not a real number!";
							}

						}catch(e){
							return doError(input, appMessage.INVALID);
						}

					}else{

						if (input.attr('span') != null) {
							var _value = new Array();
							for (span = 0 ; span < input.attr('span') ; span++ )_value[span] = inputs[i+span].value;
							var value = _value.join(glue == null ? '' : glue);
							if (!checkPattern(value, option, pattern)) return doError(input, appMessage.INVALID);
						} else {
							if (!checkPattern(input.val(), option, pattern)) return doError(input, appMessage.INVALID);
						}

					}
				}

			}else{

				if(required || minimum || maximum){

					var checkCnt = $('input[name='+ input.attr('name') +']:checked', input.parents('form')).length;

					if(required && checkCnt == 0)return doError(input, appMessage.NO_BLANK);

					minimum = parseInt(minimum);
					if(/^\d+$/.test(minimum) && checkCnt < minimum)return doError(input, appMessage.TOO_LITTLE.replace("{n}", minimum));

					maximum = parseInt(maximum);
					if(/^\d+$/.test(maximum) && checkCnt > maximum)return doError(input, appMessage.TOO_MANY.replace("{n}", maximum));

					i += ($('input[name='+ input.attr('name') +']', input.parents('form')).length - 1);

				}

			}
			
		}
		
		return true;
		
	};
	
	$.fn.bytesIndicator = function(){
		
		var target = $(this.attr('target'));

		this.keyup(function(){
			target.val(this.value.bytes() + " bytes");
		});
		
		this.trigger('keyup');

	};
	
	$.fn.onlyNumbers = function(){

		$(this).keydown(function(event){
			
		    return /[0-9]|\x09|\x08|[\x61-\x6a]|[\x25-\x28]/.test(String.fromCharCode(event.keyCode));
			
		});

	};
	
})(jQuery);



function doError(input, message) {
	
	var humanName, hname = input.attr("hname");

	if(hname){
		humanName = eval('({'+ input.attr("hname") +'})')[selectedLanguage];
	}else{
		humanName = input.attr('name');
	}

	alert(message.replace("{name}", humanName));
	
	if(input.is(':text, :password, select'))input.focus();
	
	return false;
	
}	



// ½Ç¼ö Ã¼Å© 
function isRealNumber(value){
	
	return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
	
};



// Çü½Ä Ã¼Å©
function checkPattern(val, pattName, custPatt) {
    switch(pattName){

        case "SSN":
            return checkSSN(val);
            
        case "BizNo":
            return checkBizNo(val);            

        case "Custom":
            var reg = new RegExp(custPatt);
            if(!reg.test(val))
                return false;

            break;

        default:
            var regDigit =/^[0-9]+$/;
			var regInt =/^[-]{0,1}[0-9]+$/;
            var regPhone =/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/;
            var regPostno =/^[0-9]{3}-[0-9]{3}$/;
            var regMail =/^[\._a-zA-Z0-9-]+@[._a-zA-Z0-9-]+\.[a-zA-Z]+$/;
            var regDomain =/^[.a-zA-Z0-9-]+.[a-zA-Z]+$/;
            var regUrl =/^[.a-zA-Z0-9-]+.[a-zA-Z]+$/;
            var regAlpha =/^[a-zA-Z]+$/;
            var regEnglishName =/^[a-zA-Z\s]+$/;
            var regHost =/^[a-zA-Z-]+$/;
            var regHangul =/[°¡-ÆR]/;
            var regHangulEng =/[°¡-ÆRa-zA-Z]/;
            var regHangulOnly =/^[°¡-ÆR]*$/;
            //var regId = /^[a-zA-Z]{1}[a-zA-Z0-9_-]{3,30}$/;
			var regId = /^[a-z]{1}[a-z0-9]{3,7}$/;
            var regDate =/^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$/;

            if(!eval("reg"+ pattName).test(val))
                return false;

    }

    return true;

}



function checkSSN(ssn) {

    // ÁÖ¹Î¹øÈ£ÀÇ ÇüÅÂ¿Í 7¹øÂ° ÀÚ¸®(¼ºº°) À¯È¿¼º °Ë»ç
    fmt = /^\d{6}-[123456]\d{6}$/;
    if (!fmt.test(ssn)) {
        return false;
    }

    // ³¯Â¥ À¯È¿¼º °Ë»ç
    birthYear = $.inArray(ssn.charAt(7), ["1", "2", "5", "6"]) != -1 ? "19" : "20";
    birthYear += ssn.substr(0, 2);
    birthMonth = ssn.substr(2, 2) - 1;
    birthDate = ssn.substr(4, 2);
    birth = new Date(birthYear, birthMonth, birthDate);

    if ( birth.getYear() % 100 != ssn.substr(0, 2) || birth.getMonth() != birthMonth || birth.getDate() != birthDate) {
        return false;
    }

    // Check Sum ÄÚµåÀÇ À¯È¿¼º °Ë»ç
	if($.inArray(ssn.charAt(7), ["1", "2", "3", "4"]) != -1){
		buf = new Array(13);
		for (i = 0; i < 6; i++) buf[i] = parseInt(ssn.charAt(i));
		for (i = 6; i < 13; i++) buf[i] = parseInt(ssn.charAt(i + 1));

		multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
		for (i = 0, sum = 0; i < 12; i++) sum += (buf[i] *= multipliers[i]);

		if ((11 - (sum % 11)) % 10 != buf[12]) {
			return false;
		}
	}

    return true;

}



function checkBizNo(bizNo){
	
	var pattern = /([0-9]{3})-([0-9]{2})-([0-9]{5})/; 
	if (!pattern.test(bizNo)) {
		return false; 
	}
	
	var num = RegExp.$1 + RegExp.$2 + RegExp.$3;
	var cVal = 0; 
	for (var i=0; i<8; i++) 
	{ 
		var cKeyNum = parseInt(((_tmp = i % 3) == 0) ? 1 : ( _tmp  == 1 ) ? 3 : 7); 
		cVal += (parseFloat(num.substring(i,i+1)) * cKeyNum) % 10; 
	} 
	var li_temp = parseFloat(num.substring(i,i+1)) * 5 + '0'; 
	cVal += parseFloat(li_temp.substring(0,1)) + parseFloat(li_temp.substring(1,2)); 
	return (parseInt(num.substring(9,10)) == (10-(cVal%10))%10) ? true : false;	
	
}