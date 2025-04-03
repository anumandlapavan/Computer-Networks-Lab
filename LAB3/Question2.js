function displayImages() {
  const category = document.getElementById("category").value;
  const number = parseInt(document.getElementById("number").value);
  const container = document.getElementById("image-container");
  container.innerHTML = "";

  if (!category || isNaN(number)) return;

  const images = [];
  const positions = [];

  while (images.length < number) {
    const x = Math.floor(Math.random() * (container.clientWidth - 128));
    const y = Math.floor(Math.random() * (container.clientHeight - 128));

    const overlap = positions.some(
      (pos) => Math.abs(pos.x - x) < 128 && Math.abs(pos.y - y) < 128
    );

    if (!overlap) {
      positions.push({ x, y });
      const img = document.createElement("img");
      img.src = `images/${category}/${images.length + 1}.jpeg`;
      img.style.left = `${x}px`;
      img.style.top = `${y}px`;
      container.appendChild(img);
      images.push(img);
    }
  }
}
