(function ($) {
  'use strict'

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
})(jQuery)
