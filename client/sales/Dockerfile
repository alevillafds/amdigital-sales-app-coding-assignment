FROM python:3.8 AS base

ENV APP_HOME=.

WORKDIR $APP_HOME

ENV TZ 'Europe/Madrid'
RUN echo $TZ > /etc/timezone && \
    apt-get update && apt-get install --no-install-recommends \
    -y tzdata && \
    rm /etc/localtime && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata && \
    apt-get clean

RUN pip install --upgrade pip

FROM base

ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1

WORKDIR $APP_HOME

COPY requirements.txt .
RUN pip install -r requirements.txt

ADD . .