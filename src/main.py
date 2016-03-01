#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Date    : 2016-02-01 11:53:42
# @Author  : RaymondWong (549425036@qq.com)
# @Link    : github/Raymond-Wong

import os
import sys
import numpy as np

import config.Const as Const
import utils.CommonUtil as CommonUtil
import handlers.DataHandler as DataHandler
import handlers.TrainHandler as TrainHandler

# 将src文件下的所有目录都导入系统变量中方便导入

def main():
  # 读取训练集中所有用户和物品的数量
  (userSet, itemSet) = DataHandler.getUsersAndItems(Const.RAW_TRAIN_DATA_PATH)
  CommonUtil.logger("Main", "DEBUG", "获取到 " + str(len(userSet)) + " 个用户和 " + str(len(itemSet)) + " 个item")
  # 读取用户对物品的态度矩阵(正确,目标)
  targetMatrix = DataHandler.getTargetMatrix(Const.TRAIN_DATA_PATH, userSet, itemSet)
  # 随机构造用户到特征向量的映射矩阵
  userMatrix = 9 * np.random.random((len(userSet), Const.FEATURES_AMOUNT))
  userMatrix.shape = (len(userSet), Const.FEATURES_AMOUNT)
  # 随机构造特征向量到物品的映射矩阵
  itemMatrix = 9 * np.random.random((Const.FEATURES_AMOUNT, len(itemSet)))
  itemMatrix.shape = (Const.FEATURES_AMOUNT, len(itemSet))
  # 开始迭代用户矩阵和物品矩阵
  (userMatrix, itemMatrix) = TrainHandler.iterateTrain(targetMatrix, userMatrix, itemMatrix)
  # 输出结果矩阵
  print np.dot(userMatrix, itemMatrix)

if __name__ == "__main__":
  startKey = CommonUtil.startProcess('Main', '主程序')
  main()
  CommonUtil.endProcess(startKey)
