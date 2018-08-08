# LevelWorld
# 世界等级

### 简介
限制玩家进入某个世界必须要满足特定等级。

### 指令
```yaml
/lvlworld level [lvl]    查看/设置当前世界的等级需求
/lvlworld default [lvl]  查看/设置默认世界的等级需求
/lvlworld force          设置当前位置为强制重生点
```

### 权限
```yaml
lvlworld.admin     指令权限 , 默认 OP
lvlworld.bypass    越权权限 , 无视等级限制 , 默认 OP
```

### 注意
1. 强制重生点必须设置，某些不可取消的传送事件是无法阻止的，只能通过转移到强制重生点实现折衷的效果。
2. 强制重生点所在的世界将无视等级限制，任意玩家都可进入。
