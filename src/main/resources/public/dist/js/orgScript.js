(function ($) {
  'use strict'

  // 新規会員登録 入力チェック処理
  $('#memberForm').validate({
    rules: {
      member_name: {
        required: true
      },
      email: {
        required: true,
        email: true,
      },
      password: {
        required: true,
        minlength: 4
      },
    },
    messages: {
      member_name: "名前は必須入力です。",
      email: {
        required: "Emailは必須入力です。",
        email: "Emailの形式に誤りがあります。"
      },
      password: {
        required: "Passwordは必須入力です。",
        minlength: "Passwordは4文字以上入力して下さい。"
      },
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
      error.addClass('invalid-feedback');
      element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
      $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
      $(element).removeClass('is-invalid');
    }
  });

  // 個人情報管理 会員編集画面 入力チェック処理
  $('#modMemberForm').validate({
    rules: {
      'mod.member_name': {
        required: true
      },
      'mod.email': {
        required: true,
        email: true,
      },
      'mod.password': {
        required: true,
        minlength: 4
      },
    },
    messages: {
      'mod.member_name': "名前は必須入力です。",
      'mod.email': {
        required: "Emailは必須入力です。",
        email: "Emailの形式に誤りがあります。"
      },
      'mod.password': {
        required: "Passwordは必須入力です。",
        minlength: "Passwordは4文字以上入力して下さい。"
      },
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
      error.addClass('invalid-feedback');
      element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
      $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
      $(element).removeClass('is-invalid');
    }
  });

  // 在庫管理 商品編集画面 入力チェック処理
  $('#itemForm').validate({
    rules: {
      item_name: {
        required: true
      },
      stock_cnt: {
        required: true,
        digits: true,
      },
      order_cnt: {
        required: true,
        digits: true,
      },
      can_rental_date: {
        required: true,
        dateISO: true,
      },
      add_point: {
        required: true,
        digits: true,
      },
    },
    messages: {
      item_name: "商品名は必須入力です。",
      stock_cnt: {
        required: "在庫数は必須入力です。",
        digits: "在庫数は数値のみ入力可能です。"
      },
      order_cnt: {
        required: "発注点は必須入力です。",
        digits: "発注点は数値のみ入力可能です。"
      },
      can_rental_date: {
        required: "開始日は必須入力です。",
        dateISO: "開始日の形式に誤りがあります。",
      },
      add_point: {
        required: "ポイントは必須入力です。",
        digits: "ポイントは数値のみ入力可能です。"
      },
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
      error.addClass('invalid-feedback');
      element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
      $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
      $(element).removeClass('is-invalid');
    }
  });

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
  });

  // 在庫管理 商品編集画面 日付選択処理
    $('#reservationdate').datetimepicker({
        format: 'yyyy-mm-DD'
    });

})(jQuery)
