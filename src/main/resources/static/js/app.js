const getQuantityElement = () => document.querySelector('#quantity');
const getCryptoDropdown = () => document.querySelector('#cryptocurrency-dropdown');

function setTransactionValue(quantityElement, cryptoDropdown) {
  let selectedPrice = cryptoDropdown.options[cryptoDropdown.selectedIndex].dataset.price;
  if (quantityElement.value && selectedPrice) {
    document.querySelector('#transaction-value').value = (parseFloat(quantityElement.value) * parseFloat(selectedPrice)).toFixed(2);
  }
}

if (getCryptoDropdown()) {
  getCryptoDropdown().addEventListener('change', (e) => {
    setTransactionValue(getQuantityElement(), getCryptoDropdown());
  })
  
  getQuantityElement().addEventListener('change', (e) => {
    setTransactionValue(getQuantityElement(), getCryptoDropdown());
  })
}