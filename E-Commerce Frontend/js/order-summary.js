// Order Summary JavaScript

// Get cart data from localStorage
let cartItems = JSON.parse(localStorage.getItem('cart')) || [];

// DOM elements
const orderItemsContainer = document.getElementById('order-items-body');
const orderTotalElement = document.getElementById('order-total');
// const confirmOrderBtn = document.getElementById('confirm-order-btn');
const proceedPaymentBtn = document.getElementById('proceed-payment-btn');
const orderSummarySection = document.getElementById('order-summary-section');
const orderConfirmedSection = document.getElementById('order-confirmed-section');
const paymentSuccessSection = document.getElementById('payment-success-section');

// Store order data
let currentOrderData = null;

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    if (cartItems.length === 0) {
        window.location.href = 'index.html';
        return;
    }
    
    displayOrderSummary();
    setupEventListeners();
});

// Display order summary
function displayOrderSummary() {
    
    orderItemsContainer.innerHTML = '';
    let total = 0;
    
    cartItems.forEach(item => {
        const subtotal = item.price * item.quantity;
        total += subtotal;
        
        const orderItem = document.createElement('div');
        orderItem.className = 'row align-items-center mb-3 p-3 border rounded';
        orderItem.innerHTML = `
            <div class="col-md-2">
                <img src="${item.image}" alt="${item.name}" class="img-fluid rounded" style="height: 80px; object-fit: cover;">
            </div>
            <div class="col-md-6">
                <h6 class="mb-1">${item.name}</h6>
                <p class="text-muted mb-1">Product ID: ${item.id}</p>
                <p class="text-muted mb-0">${item.description}</p>
            </div>
            <div class="col-md-2 text-center">
                <span class="badge bg-secondary">Qty: ${item.quantity}</span>
            </div>
            <div class="col-md-2 text-end">
                <div class="fw-bold">₹${item.price.toLocaleString()}</div>
                <div class="text-success">₹${subtotal.toLocaleString()}</div>
            </div>
        `;
        orderItemsContainer.appendChild(orderItem);
    });
    
    orderTotalElement.textContent = `₹${total.toLocaleString()}`;
}

// Setup event listeners
function setupEventListeners() {
    // confirmOrderBtn.addEventListener('click', confirmOrder);
   if (proceedPaymentBtn) {
    proceedPaymentBtn.addEventListener('click', proceedToPayment);
}
}

// Confirm order
async function confirmOrder() {
    const userId = Number(localStorage.getItem('userId'));
    if (!userId) {
        alert('Please login to place order');
        window.location.href = 'index.html';
        return;
    }
    
    // Show loading state
    const btn = document.querySelector('button[onclick="confirmOrder()"]');
if (btn) {
    btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Processing...';
    btn.disabled = true;
}
    
    const orderData = {
        userId: parseInt(userId),
        items: cartItems.map(item => ({
            productId: item.id,
            quantity: item.quantity,
            price: item.price
        })),
        totalAmount: cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0)
    };
    
    try {
        const response = await fetch('http://localhost:8080/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        });
        
        if (response.ok) {
            const result = await response.json();
            await handleCheckoutWithCarbon(result);
        } else {
            throw new Error('Failed to create order');
        }
    } catch (error) {
        console.error('Error creating order:', error);
        // Demo success for testing
        const demoResult = {
            userId: userId,
            orderId: Math.floor(Math.random() * 10000) + 1000,
            totalAmount: orderData.totalAmount,
            orderDate: new Date().toISOString(),
            orderStatus: 'PENDING',
            paymentStatus: 'PENDING'
        };
        await handleCheckoutWithCarbon(demoResult);
    }
}
async function handleCheckoutWithCarbon(orderResult) {
    const shippingType = document.getElementById('shipping-method').value;
    const distance = parseFloat(document.getElementById('delivery-distance').value);

    const payload = {
        // userId: orderResult.userId,
        shippingType,
        distance,
        weight: 2.5
    };

    try {
        const userId = localStorage.getItem('userId');

        const response = await fetch(`http://localhost:8080/api/orders/checkout?userId=${Number(userId)}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        const result = await response.json();

        showOrderConfirmed(orderResult);
        displayCarbonResult(result, shippingType, distance);

    } catch (error) {
        console.error("Checkout Error:", error);
        showOrderConfirmed(orderResult);
    }
}
function displayCarbonResult(result, shippingType, distance) {
    // 1. Grab the Order ID element inside the Modal
    const orderIdDiv = document.getElementById('order-id-display').parentNode;

    // 2. Create a new box for the Carbon Report if it doesn't exist yet
    let carbonDiv = document.getElementById('modal-carbon-report');
    if (!carbonDiv) {
        carbonDiv = document.createElement('div');
        carbonDiv.id = 'modal-carbon-report';
        carbonDiv.className = 'alert alert-success mt-3 text-start shadow-sm';
        
        // Insert it right after the Order ID in the modal
        orderIdDiv.parentNode.insertBefore(carbonDiv, orderIdDiv.nextSibling);
    }

    // 3. Fill it with the data from Aashika SG's backend!
    // (Added fallbacks just in case the backend payload misses a key)
    carbonDiv.innerHTML = `
        <h5 class="alert-heading text-success mb-2">🌱 Environmental Impact</h5>
        <strong>Shipping Method:</strong> ${shippingType} (${distance} km) <br>
        <strong>CO2 Emitted:</strong> ${result.carbonFootprint || 'Calculated dynamically'} kg <br>
        <hr class="my-2">
        <small><em>${result.sustainabilityMessage || 'Thank you for shopping sustainably with EcoCart!'}</em></small>
    `;
}
// Show order confirmed
// Show order confirmed using the Bootstrap Modal!
function showOrderConfirmed(orderResult) {
    currentOrderData = orderResult;
    
    // 1. Put the new Order ID into the modal
    const orderIdEl = document.getElementById('order-id-display');
    if (orderIdEl && orderResult.orderId) {
        orderIdEl.textContent = '#' + orderResult.orderId;
    }

    // 2. Clear the cart so it's empty for the next order
    localStorage.removeItem('cart');

    // 3. Trigger the Bootstrap Success Modal to pop up on screen!
    const successModal = new bootstrap.Modal(document.getElementById('successModal'));
    successModal.show();
}
// Proceed to payment
async function proceedToPayment() {
    proceedPaymentBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Processing Payment...';
    proceedPaymentBtn.disabled = true;
    
    try {
        const response = await fetch(`http://localhost:8080/api/orders/${currentOrderData.orderId}/payment`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ paymentMethod: 'card' })
        });
        
        if (response.ok) {
            const result = await response.json();
            showPaymentSuccess(result);
        } else {
            throw new Error('Payment failed');
        }
    } catch (error) {
        console.error('Payment error:', error);
        // Demo payment success
        const paymentResult = {
            ...currentOrderData,
            orderStatus: 'PENDING',
            paymentStatus: 'COMPLETED'
        };
        showPaymentSuccess(paymentResult);
    }
    
    // Clear cart after payment
    localStorage.removeItem('cart');
}

// Show payment success
function showPaymentSuccess(paymentResult) {
    // Hide confirmed section and show success section
    orderConfirmedSection.classList.add('d-none');
    paymentSuccessSection.classList.remove('d-none');
    
    // Populate final details
    document.getElementById('final-user-id').textContent = paymentResult.userId;
    document.getElementById('final-order-id').textContent = paymentResult.orderId;
    document.getElementById('final-total-amount').textContent = `₹${paymentResult.totalAmount.toLocaleString()}`;
    document.getElementById('final-order-date').textContent = new Date(paymentResult.orderDate).toLocaleDateString();
    document.getElementById('final-order-status').innerHTML = `<span class="badge bg-warning">${paymentResult.orderStatus}</span>`;
    document.getElementById('final-payment-status').innerHTML = `<span class="badge bg-success">${paymentResult.paymentStatus}</span>`;
}