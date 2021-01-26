import sys
import json
import requests

from PIL import Image
import numpy as np

_IMAGE_SIZE = 64
# TensorFlow-serving 调用地址，这里要替换成自己的，后面会讲到如何安装
SERVER_URL = 'http://172.17.0.4:8501/v1/models/image:predict'
_LABEL_MAP = {0: 'drawings', 1: 'hentai', 2: 'neutral', 3: 'porn', 4: 'sexy'}


def standardize(img):
    mean = np.mean(img)
    std = np.std(img)
    img = (img - mean) / std
    return img

# 导入
def load_image(image_path):
    img = Image.open(image_path)
    img = img.resize((_IMAGE_SIZE, _IMAGE_SIZE))
    img.load()
    data = np.asarray(img, dtype="float32")
    data = standardize(data)
    data = data.astype(np.float16, copy=False)
    return data

# 分析
def nsfw_predict(image_data):
    pay_load = json.dumps({"inputs": [image_data.tolist()]})
    response = requests.post(SERVER_URL, data=pay_load)
    data = response.json()
    outputs = data['outputs']
    predict_result = {"classes": _LABEL_MAP.get(outputs['classes'][0])}
    predict_result['probabilities'] = {_LABEL_MAP.get(i): l for i, l in enumerate(outputs['probabilities'][0])}
    return predict_result


if __name__ == '__main__':
    image_data = load_image(sys.argv[1])
    predict = nsfw_predict(image_data)
    print(predict)
