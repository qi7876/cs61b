## Overview

在真正进入proj4cd前，设计我们的算法思路

我们应该作出最小的改动来完成proj4cd，控制好complexity

proj4c的目标是

> Implement the **hyponyms** button in the web interface
>
> When we click on “hyponyms”:
>
> - If given a single word:
>   - Return a string representation of a list of the hyponyms of the word, including the word itself
>   - E.g. “action” → “[action, change, demotion, variation]”
> - If given multiple words (i.e. a list of words):
>   - Return a string representation of a list of the common hyponyms of each word
>   - E.g. “change, occurrence” → “[alteration, change, increase, jump, leap, modification, saltation, transition] “
>
> In both cases, the returned list should be in alphabetical order with no repeated words

## Data

我们来看一下raw数据，这里以hyponyms_size11.txt和synsets_size11.txt为例

先看synsets_size11.txt，这里定义了一些synset，以comma作为separator，每行可以分为3列，第一列是每个synset的id，第二列是synset本身，而synset本身又可以以space作为separator分割出多个词，第三列是对这个synset的描述，这里我们可以直接忽略

```
0,action,dummy
1,change,dummy
2,demotion,dummy
3,descent,dummy
4,jump parachuting,dummy
5,increase,dummy
6,jump leap,dummy
7,augmentation,dummy
8,antihistamine,dummy
9,nasal_decongestant,dummy
10,actifed,dummy
```

处理完synsets_size11.txt后，我们就可以获得图中所有的node了

再来看一下hyponyms_size11.txt，这里给出了图的具体结构。每一行的第一个id指示synset，后续的所有id都是这个synset的hyponym

```
0,1
1,2
3,4
5,6,7
8,10
9,10
```

结合上面获得的node数据，处理完hyponyms_size11.txt后，我们就能获得完整的图了

## Components

现在让我们来思考一下怎么建起来这个graph

在处理synsets_size11.txt时，我们需要新建并记录下所有node，这时还没有建立graph结构，我们可以使用id作为key，建立起一个map，方便后面建graph时进行查询

然后是开始处理hyponyms_size11.txt，根据id为key的map，更新node中的邻接表数据，这个邻接表用Vec，并且这里应该存指针，处理完之后，graph就建起来了

但是，我们在收到请求时，找的是某个具体的词的hyponym，也就是说，我们还需要一个以word为key的map来提高查询速度

通过保留这三个object，可以实现增量更新、快速查询

## Request Flows

sequence就很简单了，调用parser，按顺序建立起3个object并返回就可以了

后续的handle需要调用以word为key的map，并将查询结果取交集

## Complexity

建三种map都是$\mathrm{O}(n)$

后续查询，按BtreeMap来建的话，应该是$\mathrm{O}(\log{n})$的复杂度



