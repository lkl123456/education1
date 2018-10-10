// JavaScript Document
$(document).ready(function(){
	var $mvb=$('.jian_atk div span').html();
	var $mvb1=$mvb.substr(0,150);
	$('.jian_atk div span').html($mvb1);
	$('.jian_atk div a').click(function(){
		$('.jian_atk div span').html($mvb);
		$(this).hide();
		$('.jian_atk div b').show();
	})
	$('.jian_atk div b').click(function(){
		$('.jian_atk div span').html($mvb1);
		$(this).hide();
		$('.jian_atk div a').show();
	})
})