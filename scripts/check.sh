#!/usr/bin/env bash
set -euo pipefail # Bash strict mode: exit on any error or undefined variable

allsucceded=0
while read -r url; do
  date=$(date -Iseconds)
  #Check per url and obtain the http code
  res=$(curl -s -o /dev/null --max-time 5 -w "$date,$url,%{http_code},%{time_total}\n" "$url" || true)
  echo $res >> results.csv
  if [[ "$res" == *"000"* ]]; then
    allsucceded=1
  fi
done < "$1"
exit "$allsucceded"