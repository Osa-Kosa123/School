<?php
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
  if (!isset($_POST['csrf_token']) || empty($_FILES['upload'])) {
    echo json_encode(['status' => 'error', 'error' => 'Missing CSRF token or file']);
    exit;
  }

  $uploadDir ='../assets/items/';
  if (!is_dir($uploadDir)) mkdir($uploadDir, 0777, true);

  foreach ($_FILES['upload']['tmp_name'] as $i => $tmp) {
    $name = basename($_FILES['upload']['name'][$i]);
    move_uploaded_file($tmp, $uploadDir . $name);
  }

  echo json_encode(['status' => 'success']);
} else {
  echo json_encode(['status' => 'error', 'error' => 'Invalid method']);
}
?>