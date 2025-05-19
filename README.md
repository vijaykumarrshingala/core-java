awk -F'[][]' '$2 ~ /27\/Apr\/2025/ && /POST \/test\/login/ {
    split($2, t, /[: ]/);
    hour = t[4]; min = t[5];
    if ((hour == 20 && min >= 37) || (hour == 21 && min <= 37)) print
}' access_log

