(function ($) {
  'use strict'

  // レンタル管理画面レコード入れ替え処理
  $('.rentalList').sortable({
    update: function(event, ui) {
      var objs = $('.rentalList tr');
      var ids = '';
      for(var i = 0; i < objs.length; i++) {
        if(i > 0) ids += ',';
        ids += objs[i].id;
      }
      $('input[name="rental_hope"]').val(ids);
      $('#rentalHope').submit();
	}
  })

})(jQuery)
