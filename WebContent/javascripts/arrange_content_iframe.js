document.title = parent.document.title;

var homeFrameUrl = self.location.href;
var identifier = ((homeFrameUrl.indexOf('?') > 0) ? homeFrameUrl.substring(homeFrameUrl.indexOf('?')+1) : "content");
var bgColor = "#ffffff";
var paddingBottom = 50;
var parentFrame = null;

/*-------------------------------------------------------------------------
function that resize iframe for message view page in all community modules
  -------------------------------------------------------------------------*/
function resizeIFrameInCommView(frameId, divId, bottomMargin){

    try{

        parentFrame.height($('#'+ divId).height() + bottomMargin);
		$('#notificator').remove();

    }catch(e){

        return;

    }

}

/*-------------------------------------------------------------------------
function that set content for message view page in all community modules
  -------------------------------------------------------------------------*/
function setContent(){

    try{

        document.write(parent.$('#'+ identifier +'TextArea').val());

    }catch(e){

        $('#notificator').html("본문을 불러오지 못했습니다. 다시 시도하여 주세요.");

    }

}


jQuery(function(){

	parentFrame = parent.$('#'+ identifier +'IFrame');

	try{
		if(parentFrame.attr("bgcolor"))bgColor = parentFrame.attr("bgcolor");
	}catch(e){}

	try{
		if(parentFrame.attr("paddingbottom"))paddingBottom = parentFrame.attr("paddingbottom");
	}catch(e){}

    setTimeout("resizeIFrameInCommView(identifier +'IFrame', 'contentDiv', "+ paddingBottom +")", 700);
	$('body').css('background-color', bgColor);

    $(document.links).each(function(){
    	
    	var _this = $(this);

        if(_this.attr('target') == '' || _this.attr('target') == '_self')_this.attr('target', '_parent');

        $('img', $(this)).each(function(img){
            img.attr('disableenlarge', 'disableenlarge');
        });

    });

	var popupWidth = 300, popupHeight = 300;
	var imageWidth = 0, imageHeight = 0;
	$(document.images).each(function(){
		
		var _this = $(this);

		_this.attr('ORIGINAL_WIDTH', imageWidth = _this.width());
		_this.attr('ORIGINAL_HEIGHT', imageHeight = _this.height());

		if(imageWidth > parentFrame.width())_this.width(parentFrame.width());

		if(imageWidth > popupWidth && _this.attr('disableenlarge') == null){

			_this.click(function(event){
				openWin('/img_view.htm?img_src='+ _this.attr('src'), popupWidth, imageHeight > popupHeight ? popupHeight + 15 : imageHeight, 'img_view', imageWidth > popupWidth || imageHeight > popupHeight);
				return false;
			});

			_this.css('cursor', 'pointer');

		}

	});

});

/*
$(document).bind('contextmenu', function(){
	return false;
}).bind('dragstart', function(){
	return false;
});

if($.browser.mozilla){
	$('body').css('MozUserSelect', 'none');
}else if($.browser.msie){
	$(document).bind('selectstart', function(){
		return false;
	});	
}else{
	$(document).mousedown(function(){
		return false;
	});
}
*/