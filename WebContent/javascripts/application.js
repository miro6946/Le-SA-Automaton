// Place your application-specific JavaScript functions and classes here
var pageId = null;

if(!navigator.cookieEnabled)alert(appMessage.disabledCookies);

//var isIE = navigator.appVersion.indexOf('MSIE') != -1;
//var isIE6 = navigator.appVersion.indexOf('MSIE 6') != -1;

//var validiatePair = {key:"", no:""};

function openWin(url, w, h, name, bScrollbar, t, l, bResizable) {

    if(bScrollbar == null)
        bScrollbar = 0;

    if(bResizable == null)
        bResizable = 0;

    if(name == null)
        name = "popWin";

	if(w >= screen.width){ //��ũ�� ���׿� ���� ��ũ�ѹ� �ڵ�ǥ��
			w = screen.width - 40;
			bScrollbar = 1;
	}

	if(h >= screen.height){ //��ũ�� ���׿� ���� ��ũ�ѹ� �ڵ�ǥ��
			h = screen.height - 40;
			w = w + 20;
			bScrollbar = 1;
	}

    if(t == null)
        t = (screen.height-h)/2;

    if(l == null)
        l = (screen.width-w)/2;

    var PopWin = window.open(url, name, "toolbar=0, channelmode=0, location=0, directories=0, resizable="+ bResizable +", menubar=0, scrollbars="+ bScrollbar +", width=" + w + ", height=" + h +", top="+t+", left="+l);

    if(PopWin == null){
        alert(appMessage.disabledPopup);
        return;
    }

    PopWin.focus();

	return PopWin;
}



function calCapacity(cap){

	cap = parseFloat(cap);

	if(cap > 1073741824)
		return Math.round(cap/1073741824) + " GB";
	else if(cap > 1048576)
		return Math.round(cap/1048576) + " MB";
	else if(cap > 1024)
		return Math.round(cap/1024) + " KB";
	else
		return Math.round(cap) + " B";
	
}

//�� ��� 
function CalPyeng(area)
{
	var pyeng = (area * 0.3025);
	return pyeng.toFixed(2);
}


function formatCurrency(number){

	var str = String(number);
	if(!isNaN(str)){

		if(str != 'Infinity'){
			var temp = [];
			if(number < 0)str = str.substr(1);

			while(str.length > 3){
				temp.push(str.slice(str.length - 3, str.length));
				str = str.slice(0, str.length - 3);
			}
			temp.push(str);

			str = temp.reverse().join(",");
			if(number < 0)str = ("-"+ str);
			return str;
		}else{
			return '��';
		}

	}else{

		return '';

	}

}



/*-------------------------------------------------------------------------
   ��Ű ��������
  -------------------------------------------------------------------------*/
function getCookie( name ){
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length )
	{
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) {
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
			endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
			
		x = document.cookie.indexOf( " ", x ) + 1;
			
		if ( x == 0 ) break;
	}
			return "";
}


/*-------------------------------------------------------------------------
   ��Ű ����
  -------------------------------------------------------------------------*/
function setCookie( name, value, expiredays ) { 

		if(expiredays != null){
			var todayDate = new Date(); 
			todayDate.setDate(todayDate.getDate() + expiredays);
			document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";";
		}else{
			document.cookie = name + "=" + escape( value ) + "; path=/;";
		}
} 

/*-------------------------------------------------------------------------
   ��Ű ����
  -------------------------------------------------------------------------*/
function delCookie (name) {  
		var exp = new Date();  
		exp.setTime (exp.getTime() - 1);  
		var cval = getCookie (name);  
		document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
}

/*-------------------------------------------------------------------------
��ü ����
-------------------------------------------------------------------------*/
function replaceAll(str, orgStr, repStr)
{
	return str.split(orgStr).join(repStr);
}


String.prototype.bytes = function(str){
	  str = this != window ? this : str;
	  var len = 0;
	  for(j = 0;j < str.length;j++){
		  var chr = str.charAt(j);
		  len += (chr.charCodeAt() > 128) ? 3 : 1; //�ѱ�(utf8)�� 3bytes
	  }
	  return len;
}

String.prototype.replaceAll = function(){
	
	var temp = new String(this), from = arguments[0], to = arguments[1];
	
	if(from.length > 0){
		while(temp.indexOf(from) > -1){temp = temp.replace(from, to);}
	}
	
	return temp;
	
}



var CommonCodes = {

	DDD:null, MBD:null, EML:null,

	init:function(group){
		if(eval('this.'+ group) == null)eval('this.set'+ group +'()');
	},

	setDDD:function(){
		this.DDD = [
			{text:'02(����)', value:'02'}, 
			{text:'031(���)', value:'031'},
			{text:'032(��õ)', value:'032'},
			{text:'033(����)', value:'033'},
			{text:'041(�泲)', value:'041'},
			{text:'042(����)', value:'042'},
			{text:'043(���)', value:'043'},
			{text:'051(�λ�)', value:'051'},
			{text:'052(���)', value:'052'},
			{text:'053(�뱸)', value:'053'},
			{text:'054(���)', value:'054'},
			{text:'055(�泲)', value:'055'},
			{text:'061(����)', value:'061'},
			{text:'062(����)', value:'062'},
			{text:'063(����)', value:'063'},
			{text:'064(����)', value:'064'},
			{text:'070(���ͳ�)', value:'070'}
		];
	},

	setMBD:function(){
		this.MBD = [
			{text:'010', value:'010'}, 
			{text:'011', value:'011'},
			{text:'016', value:'016'},
			{text:'017', value:'017'},
			{text:'018', value:'018'},
			{text:'019', value:'019'}
		];
	},

	setEML:function(){
		this.EML = [
			{text:'chollian.net', value:'chollian.net'},
			{text:'dreamwiz.com', value:'dreamwiz.com'},
			{text:'empal.com', value:'empal.com'},
			{text:'freechal.com', value:'freechal.com'},
			{text:'hanmail.net', value:'hanmail.net'},
			{text:'hotmail.com', value:'hotmail.com'},
			{text:'lycos.co.kr', value:'lycos.co.kr'},
			{text:'korea.com', value:'korea.com'},
			{text:'nate.com', value:'nate.com'},
			{text:'naver.com', value:'naver.com'},
			{text:'paran.com', value:'paran.com'},
			{text:'unitel.co.kr', value:'unitel.co.kr'},
			{text:'yahoo.co.kr', value:'yahoo.co.kr'}
		];
	},
	
	setLOS:function(){
		this.LOS = [
			{text:'�ӽ�', value:'T'},
			{text:'����', value:'R'},
			{text:'�ɻ���', value:'E'},
			{text:'���', value:'C'},
			{text:'�Ϸ�', value:'S'}
		];
	},
	
	setLOT:function(){
		this.LOT = [
			{text:'�Ϲ�����', value:'01'},
			{text:'��ȹ����', value:'02'},
			{text:'��������', value:'03'}
		];
	},
	
	setGRT:function(){
		this.GRT = [
			{text:'�ε���', value:'01'},
			{text:'������', value:'02'},
			{text:'��Ÿ', value:'03'}
		];
	},
	
	setRES:function(){
		this.RES = [
			{text:'����Ʈ', value:'01'},
			{text:'�ܵ�����', value:'02'},
			{text:'���󿬸�', value:'03'},
			{text:'��Ÿ', value:'04'}
		];
	},	
			
	writeOptions:function(group, value){
		
		this.init(group);
		$(eval('this.'+ group)).each(function(){
			document.write("<option value='"+ this.value +"'"+ (this.value != value ? "" : " selected='selected'" ) +">"+ this.text +"</option>")
		});
		
	},
	
	appendOptions:function(select, group, value){
		
		this.init(group);
		$(eval('this.'+ group)).each(function(){
			select.append($("<option value='"+ this.value +"'"+ (this.value != value ? "" : " selected='selected'" ) +">"+ this.text +"</option>"));
		});
		
	},
	
	writeText:function(group, value) {
		this.init(group);
		$(eval('this.'+ group)).each(function(){
			document.write(this.value == value ? this.text : "");
		});		
	}
	
	
};



function ajaxErrorHandler(){
	
	$(document).ajaxError(function(event, xhr, settings, exception){
		if(xhr.status != 401){
			alert(appMessage.requestHttpError.replace('?', xhr.status));
			$.unblockUI();
		}else{
			self.location.reload();
		}
	});	
	
}


//jquery extend
(function($){
	
	$.fn.locateSubMenu = function(){
		
		if(this.length){
		
			var subMenuHeight = this.height();
			var footerOffsetTop = 0, bodyOffsetTop = $('#body_row').offset().top + 10;
			var subMenuTop = bodyOffsetTop;
	
			if(subMenuTop + subMenuHeight > (footerOffsetTop = ($('#footer_row').offset().top - 50))){
				subMenuTop = footerOffsetTop - subMenuHeight;
			}
	
			if(subMenuTop < bodyOffsetTop)subMenuTop = bodyOffsetTop;
	
			this.css('top', (subMenuTop + $(window).scrollTop()) + 'px');
			
			this.data('top', subMenuTop);
		
		}
		
		return this;
	};
	
	//üũ �ڽ� ��ü ����
	$.fn.checkAll = function(){
		this.click(function(){
			var checkbox = $(this);
			$('input:checkbox[name='+ checkbox.attr('input') +']').attr('checked', checkbox.is(':checked') ? 'checked' : '');
		});
		return this;
	};

	
	//Ư�� ������ �μ�
	$.fn.printContent = function(stylesheets){
		
		if(this.length){
		
	        contentPrinter.document.open();
	        contentPrinter.document.writeln("<html><head>");
	        
	        if(stylesheets){
		        $(stylesheets).each(function(){
		        	contentPrinter.document.writeln("<link href='"+ this +"' rel='stylesheet' media='print' type='text/css' />");
		        });
	        }
	        
	        contentPrinter.document.writeln("</head><body>");
	        
	        contentPrinter.document.writeln(this.html()); // ���� ���
	        contentPrinter.document.writeln("</body></html>");
	        contentPrinter.document.close();
	
	        contentPrinter.document.execCommand('Print');	
        
		}

        return this;
		
	};	
	
	
	$.fn.selectEmail = function(){
		
		this.each(function(){
			
			var select = $(this);
		
			var input = $(select.attr('input'), select.parents('form'));
			input.attr('readonly', select.val().length > 0 ? 'readonly' : '');
			
			select.change(function(){
	
				var _this = $(this)
	
				var input = $(_this.attr('input'), _this.parents('form'));
				input.val(_this.val());
				
				if(_this.val().length > 0){
					input.attr('readonly', 'readonly');
				}else{
					input.attr('readonly', '');
					input.focus();
				}
				
			});
			
			var emailHost = input.val();
			if(emailHost.length > 0){
				
				var temp = $('option[value='+emailHost+']', select);
				if(temp.length > 0){
					temp.attr('selected', 'selected');
				}else{
					$('option:last', select).attr('selected', 'selected');
					input.attr('readonly', '');
				}
				
			}else{
				
				select.focus(function(){
	
					var _this = $(this);
					
					var input = $(_this.attr('input'), _this.parents('form'));
					input.val(_this.val());
					_this.unbind('focus');
					
				});
				
			}
		
		});
		
		return this;
		
	};
	
	$.fn.zipcode = function(option){
		
		option = $.extend({
			elementSelector : null, // ���̾ ���� ����� ������ ���ڿ� 
			zipcodeToken : '__zipcode__' // �ĺ��� 
		}, option || {});		
		
		this.click(function(){
			
			$.get('/zipcodes/index.action', function(xhr){
				
				if(option.elementSelector && $(option.elementSelector).length > 0){
					$(option.elementSelector).block({message:xhr, css:{cursor:'default', width:'350px'}});
					$('.zipcode_container').data('element', option.elementSelector);
				}else{			
			                	
			        var layerHeight = 400, layerWidth = 350;
					
					$.blockUI({message:xhr, css:{
						cursor:'default', 
						width:(layerWidth +'px'),
						height:(layerHeight +'px'),
						top: ($(window).height() - layerHeight) /2 + 'px', 
		                left:($(window).width() - layerWidth) /2 + 'px'
		                }
					});
				}
				
				$('.zipcode_container').data('zipcodetoken', option.zipcodeToken);				

				/*
				var layer =  $("<div/>");
				layer.html(xhr);
				
				layer.dialog({modal:true});
				*/
				
			});			
			
		}).css('cursor', 'pointer');
		
	};
	
	$.fn.inputCurrency = function(){
		
		this.blur(function(){
			
			var _this = $(this);
			var temp = _this.val();
			
			while(temp.indexOf(',') > -1){temp = temp.replace(',', '');}
			
			temp = parseInt(temp);
			
			if(!isNaN(temp)){
				_this.val(formatCurrency(temp));
			}else{
				_this.val(_this.data('value_was'));
			}
			
			_this.removeData('value_was');
			
		}).focus(function(){
			
			var _this = $(this);
			var temp = _this.val();
			
			while(temp.indexOf(',') > -1){temp = temp.replace(',', '');}		
			
			_this.data('value_was', _this.val());
			_this.val(temp);
			
		}).trigger('blur');		
		
	};
		
})(jQuery);


jQuery(function($){

	$('div.embed_flash').each(function() {
		var div = $(this);
		var _option = null;
		var _src = div.attr('src');
		
		try{
			_option = eval('({'+ div.attr('option') +'})');
			_option['src'] = _src;
		}catch(e){
			_option = {'src':_src};
		};
		
		div.flashembed(_option);
	});
	

	
	/*
	$('.errorMessage').each(function(){
		
		var _this = $(this);
		
		_this.hide();
		
		$.blockUI({message:_this});
		
		
	}).click(function(){
		$.unblockUI();
	}).css('cursor', 'pointer');
	*/

	$('.form_table th.required').prepend($('<img src="/images/star.jpg" class="required"/>'));
	
});

// ��� �̹��� �̺�Ʈ
$('.toggle_image').live('mouseover', function() {
	var _overStyle = $(this).attr('data');
	if (typeof _overStyle == 'undefined') {
		return false;
	}
	//_overStyle = _overStyle.replace(/`/g, '\'');
	_overStyle = eval('({' + _overStyle + '})');
	$(this).attr('src', _overStyle.over);
	var preloadImage = new Image();
	preloadImage.src = _overStyle.over;
}).live('mouseout', function() {
	var _outStyle = $(this).attr('data');
	if (typeof _outStyle == 'undefined') {
		return false;
	}
	//_outStyle = _outStyle.replace(/`/g, '\'');
	_outStyle = eval('({' + _outStyle + '})');
	$(this).attr('src', _outStyle.out);
});

$('input.auto_focus[maxlength]').live('keyup', function(){
	
	var _this = $(this);
	
	if(_this.val().length == parseInt(_this.attr('maxlength'))){
		var selector = _this.attr('next');
		if(selector == null){
			_this.next('input, select', this.form).focus();
		}else{
			$(selector, this.form).focus();
		}
	}
	
});

$('select.auto_focus').live('change', function(){
	
	var _this = $(this);

	var selector = _this.attr('next');
	if(selector == null){
		_this.next('input, select', this.form).focus();
	}else{
		$(selector, this.form).focus();
	}		
	
});