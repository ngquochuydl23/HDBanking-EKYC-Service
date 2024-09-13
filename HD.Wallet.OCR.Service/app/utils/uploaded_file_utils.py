def check_file_extension(file):
    return file.filename.split(".")[-1] in ("jpg", "jpeg", "png", "webp")
