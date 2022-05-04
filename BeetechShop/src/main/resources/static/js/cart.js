 $(document).ready(function() {
	
	// Tính tổng tiền cho mỗi loại sản phẩm:
	handlePrice();
	
	// Tăng giảm số lượng đã chọn:
	$('.product-cart__btn_left').click(function(e) {
		let quantitySelected = $(e.target).next();
		let value = Number(quantitySelected.val());
		if(value >= 2) {
			value = Number(quantitySelected.val()) - 1;
			$(e.target).next().val(value);
			handlePrice();
		}
		console.log($('.t-quantity-selected').val())
	})
	
	$('.product-cart__btn_right').click(function(e) {
		let quantitySelected = $(e.target).prev();
		let value = Number(quantitySelected.val());
		quantitySelected.val( value + 1 );
		handlePrice();
		console.log($('.t-quantity-selected').val())
	})
	
	
	// Click xóa sản phẩm trong giỏ hàng:
	$('.product-cart__delete').click(deleteProductInCart.bind(this));
	
	// Click thanh toán:
	$('.t-pay').click(handlePayment);
	
		
})

/*
	Hàm xử lý tính giá tiền.
	Created by: NPTAN (28/04/2022)
	Version: 1.0
 */
function handlePrice() {
	let prices = $('.product-cart__price-old');
	let quantitys = $('.product-cart__quantity');
	let totalPrices = $('.product-cart__price');
	let totalCart = 0;
	for(let i = 0 ; i < prices.length ; i++) {
		let price = Number($(prices[i]).text());
		let quantity = Number($(quantitys[i]).val());
		$(totalPrices[i]).text(price * quantity);
		totalCart += (price * quantity);
	}
	$('.product-pay__price').text(totalCart);	
}


/*
	Hàm xử lý xóa sản phẩm khỏi giỏ hàng.
	Created by: NPTAN (28/04/2022)
	Version: 1.0
 */
function deleteProductInCart(e) {
	
	console.log(e.target);
	
	// Token:
	let token = $('.t-token').text();
	
	if( token === 'null' ) {
		// Lấy product id:
		let productId = Number($(e.target).next().text());
		// Nếu chưa đăng nhập -> gọi đến API clone -> xóa trong session:
		$.ajax({
	        type: "DELETE",
	        url: `http://localhost:8081/clone/cart/${productId}`,
	        success: function (response) {
	            alert("Xóa thành công.");
	            window.location.reload();
	        }
	    });	
	}
	else {
		// Nếu đã đăng nhập -> gọi API xóa trong databse:
		// Gọi đến API, xóa sản phẩm trong giỏ hàng theo cartId:
		// Lấy cart id của sản phẩm:
		let cartId = Number($(e.target).prev().text());
		$.ajax({
	        type: "DELETE",
	        url: `http://localhost:8081/cart/${cartId}`,
	        success: function (response) {
	            alert("Xóa thành công.");
	            window.location.reload();
	        }
	    });
	}
}


/*
	Hàm xử lý khi người dùng nhấn thanh toán.
	Created by: NPTAN(29/04/2022)
	Version: 1.0
*/
function handlePayment() {
	
	// Token:
	let token = $('.t-token').text();
	
	// Nếu chưa đăng nhập -> chuyển tới trang đăng nhập rồi mới được thanh toán:
	if( token === 'null' ) {
		alert("Đăng nhập tài khoản để thanh toán.");
		window.location.href = "/auth/login";
		return;
	}

	// Kiểm tra giỏ hàng trước khi thanh toán:
	let quantitys = $('.t-quantity');	// Số lượng còn lại
	let quantitySelecteds = $('.t-quantity-selected');	// Số lượng đã chọn
	let productNames = $('.t-product-name');	// Tên sản phẩm
	// 1. Kiểm tra xem trong giỏ hàng có sản phẩm nào hay chưa?
	if( productNames.length == 0 ) {
		alert("Bạn chưa có sản phẩm nào trong giỏ hàng.");
		return;
	}
			
	// 2. Kiểm tra số lượng sản phẩm có đủ hay không?
	for ( let i = 0 ; i < quantitys.length ; i++ ) {
		let quantity = Number( $(quantitys[i]).text() );
		let quantitySelected = Number( $(quantitySelecteds[i]).val() );
		let productName = $(productNames[i]).text();
		
		if( quantity < quantitySelected ) {
			alert("Số lượng " + productName.toLowerCase() + " không đủ.")
			return;
		}
	}
	
	// Kiểm tra phương thức thanh toán:
	let payMethod = $('input[name="t-pay-method"]:checked').attr('id');
	
	console.log(payMethod)
	// Nếu thanh toán offline -> thông báo thành công 
	// 1. Tạo hóa đơn
	// 2. xóa các sản phẩm trong giỏ hàng:
	if( payMethod === 't-pay-offline' ) {
		// Lấy user id:
		let userId = Number($('.t-user-id').text());
		
		
		// Gọi API tạo hóa đơn:
		
		// Reset sản phẩm trong giỏ hàng
		resetCart();
	}
	else if( payMethod === 't-pay-online' ) {
		window.location.href = "http://localhost:8081/payment";
	}
	
}

/*
	Xử lý reset danh sách sản phẩm trong giỏ hàng.
	Created by: NPTAN (29/04/2022)
	Version: 1.0
*/
function resetCart() {
	// Update số lượng sản phẩm còn lại trong database
	$.ajax({
        type: "DELETE",
        url: `http://localhost:8081/pay`,
        success: function (response) {
        	alert("Đặt hàng thành công, đơn hàng sẽ được giao trong vài ngày tới.");
        	window.location.href = "http://localhost:8081";
        }
    });
}



