#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Date    : 2016-02-01 14:19:07
# @Author  : RaymondWong (549425036@qq.com)
# @Link    : github/Raymond-Wong

import os
import sys
import numpy as np
sys.path.append('..')

import config.Const as Const
import utils.CommonUtil as CommonUtil

# 开始做迭代计算
def train(targetMatrix, userMatrix, itemMatrix, lr):
  # 计算当前误差
  # errorMatrix = targetMatrix - np.dot(userMatrix, itemMatrix)
  # 根据误差调整物品矩阵
  # newItemMatrix = itemMatrix + lr * (np.dot(errorMatrix.transpose(), userMatrix).transpose() - Const.OVERFITTING * itemMatrix)
  # 根据误差调整用户矩阵
  # newUserMatrix = userMatrix + lr * (np.dot(errorMatrix, itemMatrix.transpose()) - Const.OVERFITTING * userMatrix)
  errorMatrix = targetMatrix - np.dot(userMatrix, itemMatrix)
  for user in xrange(0, targetMatrix.shape[0]):
    for item in xrange(0, targetMatrix.shape[1]):
      if targetMatrix[user][item] == 0.0:
        continue
      for k in xrange(0, Const.FEATURES_AMOUNT):
        userMatrix[user][k] += lr * (errorMatrix[user][item] - Const.OVERFITTING * userMatrix[user][k])
        itemMatrix[k][item] += lr * (errorMatrix[user][item] - Const.OVERFITTING * itemMatrix[k][item])
  return (userMatrix, itemMatrix)

# 获取误差值
def getError(targetMatrix, userMatrix, itemMatrix):
  # tmpMatrix = targetMatrix - np.dot(userMatrix, itemMatrix)
  # tmpMatrix.shape = targetMatrix.shape
  # itemTmpMatrix = sum(itemMatrix)
  # userTmpMatrix = sum(userMatrix.transpose()).transpose()
  # userTmpMatrix.shape = (userTmpMatrix.shape[0], 1)
  # tmpMatrix = tmpMatrix * tmpMatrix + Const.OVERFITTING * (itemTmpMatrix * itemTmpMatrix) + Const.OVERFITTING * (userTmpMatrix * userTmpMatrix)
  # error = 0.0
  # for user in xrange(0, targetMatrix.shape[0]):
  #   for item in xrange(0, targetMatrix.shape[1]):
  #     if targetMatrix[user][item] != -2.0:
  #       error += tmpMatrix[user][item]
  # return error
  errorMatrix = targetMatrix - np.dot(userMatrix, itemMatrix)
  error = 0.0
  for user in xrange(0, targetMatrix.shape[0]):
    for item in xrange(0, targetMatrix.shape[1]):
      if targetMatrix[user][item] != 0.0:
        error += pow(errorMatrix[user][item], 2)
  return error

# 迭代调整矩阵
def iterateTrain(targetMatrix, userMatrix, itemMatrix):
  startKey = CommonUtil.startProcess('TrainHandler', '迭代调整矩阵')
  learing_rate = Const.LEARNING_RATE
  looper = 1
  lastError = None
  while True:
    (userMatrix, itemMatrix) = train(targetMatrix, userMatrix, itemMatrix, learing_rate)
    learing_rate *= 0.9
    thisError = getError(targetMatrix, userMatrix, itemMatrix)
    CommonUtil.logger("TrainHanlder", "INFO", str(looper) + " 次迭代的误差为 " + str(thisError))
    looper += 1
    # if lastError is not None and thisError >= lastError:
    if looper > Const.ITERATE_TIME:
      break
    lastError = thisError
  CommonUtil.endProcess(startKey)
  return (userMatrix, itemMatrix)