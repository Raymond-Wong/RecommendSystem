#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Date    : 2016-02-01 12:15:47
# @Author  : RaymondWong (549425036@qq.com)
# @Link    : github/Raymond-Wong

import os
import sys
import numpy as np
sys.path.append("..")

import utils.CommonUtil as CommonUtil

# 获取文件的行数
def getRowsCount(path):
  # 尝试直接readlines看是否成功
  try:
    return len(open(path, 'rU').readlines())
  except Exception:
    # 如果文件太大无法直接将所有行读入内存,则逐行读取并做计数
    counter = 0
    f = open(path, 'rU')
    line = f.readline()
    while line:
      counter += 1
      line = f.readline()
    f.close()
    return counter

# 获取用户和item数组
def getUsersAndItems(path):
  startKey = CommonUtil.startProcess('DataHandler', ' 获取user和item数组')
  userSet = set()
  itemSet = set()
  f = open(path, 'rU')
  line = f.readline()
  looper = 1
  while line:
    CommonUtil.logger('DataHandler', 'DEBUG', '正在读取 ' + path + ' 的第 ' + str(looper) + ' 行')
    looper += 1
    user, item, attitude, timestamp = line.split('\t')
    userSet.add(user.strip())
    itemSet.add(item.strip())
    line = f.readline()
  userSet = list(userSet)
  itemSet = list(itemSet)
  CommonUtil.endProcess(startKey)
  return (userSet, itemSet)

# 获取真实的用户对物品的态度矩阵(稀疏)
def getTargetMatrix(path, userSet, itemSet):
  # 初始化一个待返回的矩阵
  retMatrix = np.array([[0.0] * len(itemSet)] * len(userSet), dtype=np.float64)
  # 读取数据集,开始构造矩阵
  f = open(path, 'rU')
  line = f.readline()
  while line:
    user, item, attitude, timestamp = line.split('\t')
    retMatrix[userSet.index(user)][itemSet.index(item)] = float(attitude)
    line = f.readline()
  return retMatrix
