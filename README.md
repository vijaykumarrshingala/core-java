#!/bin/bash

# Output file
OUTPUT_FILE="/var/log/cpu_ram_stats.log"

# Create the file and add headers if not present
if [ ! -f "$OUTPUT_FILE" ]; then
  printf "%-20s | %-15s | %-15s | %-12s | %-12s | %-18s | %-15s\n" \
    "Timestamp" "CPU_Utilized(%)" "CPU_Free(%)" "RAM_Used(MB)" "RAM_Free(MB)" "RAM_Utilized(%)" "RAM_Free(%)" \
    >> "$OUTPUT_FILE"
  printf "%s\n" "$(printf '=%.0s' {1..120})" >> "$OUTPUT_FILE"
fi

while true; do
  TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')

  # CPU utilization
  CPU_IDLE=$(top -bn1 | grep "Cpu(s)" | awk -F'id,' '{ split($1, vs, ","); v=vs[length(vs)]; gsub(/[^0-9.]/, "", v); print v }')
  CPU_UTILIZED=$(printf "%.2f" "$(echo "100 - $CPU_IDLE" | bc)")
  CPU_FREE=$(printf "%.2f" "$CPU_IDLE")

  # RAM usage (in MB)
  read total used free <<< $(free -m | awk '/^Mem:/ {print $2, $3, $4}')
  RAM_UTILIZED_PERCENT=$(printf "%.2f" "$(echo "scale=2; $used*100/$total" | bc)")
  RAM_FREE_PERCENT=$(printf "%.2f" "$(echo "scale=2; $free*100/$total" | bc)")

  # Print nicely formatted output
  printf "%-20s | %-15s | %-15s | %-12s | %-12s | %-18s | %-15s\n" \
    "$TIMESTAMP" "$CPU_UTILIZED" "$CPU_FREE" "$used" "$free" "$RAM_UTILIZED_PERCENT" "$RAM_FREE_PERCENT" \
    >> "$OUTPUT_FILE"

  sleep 60
done
