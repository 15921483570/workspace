/**
 * Created by fangchaolai on 2015/10/28.
 */


//默认下拉框，隐藏
var selectOptionsIsshow = false;


$(".first_pull").click(function () {
    $('.wrap').animate({scrollTop: 900}, 800);
});


/*6.下拉框 hover变换*/
$("#lan_china").hover(function () {
        $(this).css('backgroundColor', '#4e52a2'),
            $("#lan_china_font").css('color', '#ffffff')
    },
    function () {
        $(this).css('backgroundColor', '#ffffff'),
            $("#lan_china_font").css('color', '#4e52a2')
    });
$("#lan_fan").hover(function () {
    $(this).css('backgroundColor', '#4e52a2'),
        $("#lan_fan_font").css('color', '#ffffff')
}, function () {
    $(this).css('backgroundColor', '#ffffff'),
        $("#lan_fan_font").css('color', '#4e52a2')
});
$("#lan_en").hover(function () {
        $(this).css('backgroundColor', '#4e52a2'),
            $("#lan_english_font").css('color', '#ffffff')
    },
    function () {
        $(this).css('backgroundColor', '#ffffff'),
            $("#lan_english_font").css('color', '#4e52a2')
    });


/* 7.点击显示下拉框*/
$(".lan_switch").click(function () {

    $(".select_options").css('display', 'block');
    selectOptionsIsshow = true;
});


/*点击事件*/
$(function () {
    document.onmousedown = function (event) {
        /*
        *closest() 方法获得匹配选择器的第一个祖先元素，从当前元素开始沿 DOM 树向上。
        * event.target.tagName:触发的元素标签
        * */
        if (event.target.tagName === 'a' || $(event.target).closest('a').length > 0) {
            return;
        }

        setTimeout(function(){
            if (selectOptionsIsshow == true) {
                //显示，就隐藏
                $(".select_options").css('display', 'none');
                selectOptionsIsshow = false;
            }
        },17);


    }
});

































