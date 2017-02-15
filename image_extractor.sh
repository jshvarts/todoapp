#!/usr/bin/env bash
SOURCE=${MATERIAL_SOURCE}

if [[ -z "$SOURCE" ]]; then
    SOURCE=~/src/material-design-icons
fi

mkdir -p app/src/main/res/drawable-hdpi
mkdir -p app/src/main/res/drawable-mdpi
mkdir -p app/src/main/res/drawable-xhdpi
mkdir -p app/src/main/res/drawable-xxhdpi
mkdir -p app/src/main/res/drawable-xxxhdpi

cp ${SOURCE}/$1/drawable-mdpi/$2.png ./app/src/main/res/drawable-mdpi
cp ${SOURCE}/$1/drawable-hdpi/$2.png ./app/src/main/res/drawable-hdpi
cp ${SOURCE}/$1/drawable-xhdpi/$2.png ./app/src/main/res/drawable-xhdpi
cp ${SOURCE}/$1/drawable-xxhdpi/$2.png ./app/src/main/res/drawable-xxhdpi
cp ${SOURCE}/$1/drawable-xxxhdpi/$2.png ./app/src/main/res/drawable-xxxhdpi
