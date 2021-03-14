$(document).ready(function(){

    $.fn.serializeObject = function() {
        var obj = null;
        try {
            // this[0].tagName이 form tag일 경우
            if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) {
                var arr = this.serializeArray();
                if(arr){
                    obj = {};
                    jQuery.each(arr, function() {
                            // obj의 key값은 arr의 name, obj의 value는 value값
                        obj[this.name] = this.value;
                    });
                }
            }
        }catch(e) {
            alert(e.message);
        }finally{

        }
        return obj;
    };

    $.sendPost=function(url, objForm){
        var result = "";

        $.ajax({
            async: false,
            contentType : "application/json",
            type: "post",
            url: url,
            data: $(objForm).serialize(),
            dataType: "json",
            success: function(data, status, xhr) {
                result = data;
            }
        });

        return result;
    };


    $.sendFormData=function(url, vForm){
        var result = "";

        $.ajax({
            async: false,
            contentType : "application/json;charset=UTF-8",
            type: "post",
            url: url,
            data : JSON.stringify(vForm.serializeObject()),
            dataType: "json",
            success: function(data, status, xhr) {
                result = data;
            }
        });

        return result;
    }

    $.sendData=function(url, jsonData){
        var result = "";
        $.ajax({
            async: false,
            contentType : "application/json",
            type: "post",
            url: url,
            data : JSON.stringify(jsonData),
            dataType: "json",
            success: function(data, status, xhr) {
                result = data;
            }
        });

        return result;
    }


    $.ajaxSetup({
        cache : false,
        beforeSend: function(xhr){
            xhr.setRequestHeader("AJAX", true);
        },
        error: function(xhr, status, error){
            if (xhr.status == 400) {
                //로그인 정보 없음 처리부..
            }else{
                if(xhr.status != 0){//같은 url이 아닐경우 나는 오류 localhost, 127.0.0.1 테스트시 발생
                    alert("ajaxSetup error ::" + JSON.stringify(error));
                    console.log("status :" + status);
                    console.log("xhr:" + xhr.status);
                    console.log("error:" + error);
                }
            }
        }

    });
});

function sessionClear(){
    $.ajax({
        url: "/myp/sessionClear.do",
        type:"POST",                
        success:function(data) {
        }
    });
}

function layerPopupOn(popupId){
    var scr_width = jQuery(window).width();
    var scr_height = jQuery(window).height();
    
    var dtype = popupId;
    
    jQuery("aside, #"+dtype).show();
    
    var aswrap2w = (scr_width - jQuery("#"+dtype).width())/2;
    var aswrap2h = (scr_height - jQuery("#"+dtype).height())/2;
    jQuery("#"+dtype+".as_wrap2").css({"margin":aswrap2h+"px 0 0 "+aswrap2w+"px"});
}

function layerPopupOff(){
    jQuery("html, body").css({"overflow":"auto"});
    jQuery("aside,.as_wrap,.as_wrap2,.as_wrap3").hide();
}

function chnl_close(chnlCd){
    
    if( chnlCd == "KIAP" ){
        var param = {
                     payment : "N" // 결제 성공에 대한 값 추후를 위해 넣어주시면 좋을 것 같습니다.
        };
        if(typeof exNative == "object"){
            exNative.closeView(JSON.stringify(param));
        }else{
                     setTimeout(function(){
                                 exNative.closeView(JSON.stringify(param))
                     }, 300);
        }
    }else{
        bwc_close();
    }
}

function sessionNotiDisplayUpdate(){
    $.ajax({
        url: "/cmm/sessionNotiDisplayUpdate.do",
        type:"POST",                
        success:function(data) {
        }
    });
}
