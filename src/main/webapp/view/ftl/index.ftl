<h2>MovieTheater web app</h2>
<a href="/retrieve/">Data retrieve</a></br>
<a href="/booking">Booking</a>

<h2>Batch Upload</h2>

<form method="post" action="/batch-upload/users" enctype="multipart/form-data">
    Users file: <input type="file" name="users">
    <input type="submit" value="Upload">
</form>

<form method="post" action="/batch-upload/events" enctype="multipart/form-data">
    Events file: <input type="file" name="events">
    <input type="submit" value="Upload">
</form>