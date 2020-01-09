(function($){
	
	const registerForm = $('#registervalidation');
	const loginForm = $('#loginvalidation');

	$.validator.setDefaults({
		  debug: false,
		  success: "valid"
		});
	$(registerForm).validate({
		rules:{
			name: {
				required: true,
				minlength: 3
			},
			password: {
				required: true,
				minlength: 6
			}
		},
		messages: {
			name:{
				required: "Invalid name",
				minlength: "Too short name"
			},
			password:{
				required: "Invalid password",
				minlength: "Password must be 6 character long"	
			}
		}
	});


	$(loginForm).validate({
		rules:{
			name: {
				required: true,
				minlength: 3
			},
			password: {
				required: true,
				minlength: 6
			}
		},
		messages: {
			name:{
				required: "Invalid login name or name doesn't exists",
				minlength: "Too short name"
			},
			password:{
				required: "Invalid password",
				minlength: "Password must be 6 character long"	
			}
		}
	});
})(jQuery);




