#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Date    : 2016-02-01 11:57:40
# @Author  : RaymondWong (549425036@qq.com)
# @Link    : github/Raymond-Wong

import os
import sys

# 判断程序是否要显示debug的log信息
DEBUG = False

# 所有数据集的基础路径
BASE_DATA_PATH = "/home/raymondwong/code/recommendersystem/data"

# 训练集原始数据放置的路径
RAW_TRAIN_DATA_PATH = os.path.join(BASE_DATA_PATH, "rec_log_train_10.txt")

# 将原始的训练集数据进行切分后的真实训练集数据
TRAIN_DATA_PATH = os.path.join(BASE_DATA_PATH, "rec_log_train_10.txt")

# 隐空间的特征数量
FEATURES_AMOUNT = 2

# 学习速率
LEARNING_RATE = 0.1

# 防止过拟合参数
OVERFITTING = 0.1
