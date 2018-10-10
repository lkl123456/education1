///////////////////////////////////////////////////////////////////*
//功能介绍：用于设置横排滚动图片的jQuery插件 陈嗣圣
//作者：陈嗣圣
//时间：2012年2月10日
//
//参数列表：
//btnLeft:图片向左滚动按钮：jQuery对象或选择器
//btnRight：图片向右滚动按钮：jQuery对象或选择器
//sliderBox：滚动屏幕：jQuery对象或选择器
//sliderInner：滚动后幕：jQuery对象或选择器
//sliderItem：滚动元素：jQuery对象或选择器
//sliderDivider：滚动间隔元素：jQuery对象或选择器
//itemCount：滚动元素的总数量
//showCount：展示元素的最大数量
//
//使用说明：
//DOM说明
//sliderBox包含sliderInner包含sliderItem和sliderDivider
//sliderItem与sliderDivider交替出现，slider数量比sliderDivider数量多1
//sliderBox需设置宽度，要求其宽度可恰好展示showCount个元素以及showCount-1个滚动间隔元素，且overflow:hidden
//sliderInner需设置宽度，要求其宽度必须足够容纳所有的滚动元素和滚动间隔元素横排展示
//
//具体使用方法请参见示例
//如有问题请联系：iviedsky@live.com sschen2@iflytek.com
//////////////////////////////////////////////////////////////////*/

 $(document).ready(function () {

        $(document).setSlider({
            btnLeft: $("#btnRight"),
            btnRight: $("#btnLeft"),
                sliderInner: $("#sliderInner"),
                sliderItem: $(".sliderItem"),
                sliderDivider: $(".sliderDivider"),
                itemCount: 8,
                showCount: 5
        });
    });

(function ($) {
    $.fn.extend({
        "setSlider": function (setting) {

            var ps = $.extend({
                btnLeft: $("#btnLeft"),
                btnRight: $("#btnRight"),
                sliderInner: $("#sliderInner"),
                sliderItem: $(".sliderItem"),
                sliderDivider: $(".sliderDivider"),
                itemCount: 8,
                showCount: 5
            }, setting);

            ps.btnLeft = (typeof ps.btnLeft == 'string' ? $(ps.btnLeft) : ps.btnLeft);
            ps.btnRight = (typeof ps.btnRight == 'string' ? $(ps.btnRight) : ps.btnRight);
            ps.sliderInner = (typeof ps.sliderInner == 'string' ? $(ps.sliderInner) : ps.sliderInner);
            ps.sliderItem = (typeof ps.sliderItem == 'string' ? $(ps.sliderItem) : ps.sliderItem);
            ps.sliderDivider = (typeof ps.sliderDivider == 'string' ? $(ps.sliderDivider) : ps.sliderDivider);

            var marginLeft = 0;
            var marginLeftOffset = -(parseInt(ps.sliderItem.css("width")) + parseInt(ps.sliderDivider.css("width")));
            var marginLeftMax = (ps.itemCount - ps.showCount) * marginLeftOffset;


            ps.btnLeft.click(function () {
                if (marginLeft <= marginLeftMax) {
                    return false;
                };
                marginLeft += marginLeftOffset;
                ps.sliderInner.animate({
                    marginLeft: marginLeft
                },
                            1000,
                            function () {
                            });
            });

            ps.btnRight.click(function () {
                if (marginLeft >= 0) {
                    return false;
                };
                marginLeft -= marginLeftOffset;
                ps.sliderInner.animate({
                    marginLeft: marginLeft
                },
                            1000,
                            function () {
                            });
            });

            return $(this); //返回jQuery对象，保持链式操作
        }
    });
})(jQuery);

