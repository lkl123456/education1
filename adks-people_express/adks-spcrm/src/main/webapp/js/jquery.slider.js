///////////////////////////////////////////////////////////////////*
//���ܽ��ܣ��������ú��Ź���ͼƬ��jQuery��� ����ʥ
//���ߣ�����ʥ
//ʱ�䣺2012��2��10��
//
//�����б�
//btnLeft:ͼƬ���������ť��jQuery�����ѡ����
//btnRight��ͼƬ���ҹ�����ť��jQuery�����ѡ����
//sliderBox��������Ļ��jQuery�����ѡ����
//sliderInner��������Ļ��jQuery�����ѡ����
//sliderItem������Ԫ�أ�jQuery�����ѡ����
//sliderDivider���������Ԫ�أ�jQuery�����ѡ����
//itemCount������Ԫ�ص�������
//showCount��չʾԪ�ص��������
//
//ʹ��˵����
//DOM˵��
//sliderBox����sliderInner����sliderItem��sliderDivider
//sliderItem��sliderDivider������֣�slider������sliderDivider������1
//sliderBox�����ÿ�ȣ�Ҫ�����ȿ�ǡ��չʾshowCount��Ԫ���Լ�showCount-1���������Ԫ�أ���overflow:hidden
//sliderInner�����ÿ�ȣ�Ҫ�����ȱ����㹻�������еĹ���Ԫ�غ͹������Ԫ�غ���չʾ
//
//����ʹ�÷�����μ�ʾ��
//������������ϵ��iviedsky@live.com sschen2@iflytek.com
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

            return $(this); //����jQuery���󣬱�����ʽ����
        }
    });
})(jQuery);

